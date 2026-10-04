package com.example.builtbyd.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.media.ToneGenerator
import android.util.Log
import com.example.builtbyd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

class SoundEngine(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val prefs = context.getSharedPreferences("built_by_d_audio_prefs", Context.MODE_PRIVATE)

    private var voicePlayer: MediaPlayer? = null
    private var chimePlayer: MediaPlayer? = null
    private var musicPlayer: MediaPlayer? = null

    private val _isVoicePlaying = MutableStateFlow(false)
    val isVoicePlaying: StateFlow<Boolean> = _isVoicePlaying.asStateFlow()

    private val _isMusicPlaying = MutableStateFlow(false)
    val isMusicPlaying: StateFlow<Boolean> = _isMusicPlaying.asStateFlow()

    private val _isSynthesizing = MutableStateFlow(false)
    val isSynthesizing: StateFlow<Boolean> = _isSynthesizing.asStateFlow()

    private val _elevenLabsKey = MutableStateFlow(prefs.getString("eleven_labs_api_key", "") ?: "")
    val elevenLabsKey: StateFlow<String> = _elevenLabsKey.asStateFlow()

    private val _elevenLabsVoiceId = MutableStateFlow(prefs.getString("eleven_labs_voice_id", "21m00Tcm4TlvDq8ikWAM") ?: "21m00Tcm4TlvDq8ikWAM")
    val elevenLabsVoiceId: StateFlow<String> = _elevenLabsVoiceId.asStateFlow()

    // Pre-computed pure PCM crystal bell chime buffer (E5 + E6 + B6 crystal shimmer)
    private val chimePcmBuffer: ShortArray by lazy {
        val sampleRate = 44100
        val durationSeconds = 1.2
        val numSamples = (durationSeconds * sampleRate).toInt()
        val buffer = ShortArray(numSamples)
        val f1 = 659.25 // E5
        val f2 = 1318.5 // E6
        val f3 = 1975.5 // B6
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = exp(-3.2 * t)
            val wave = 0.50 * sin(2.0 * PI * f1 * t) +
                       0.30 * sin(2.0 * PI * f2 * t) +
                       0.20 * sin(2.0 * PI * f3 * t)
            buffer[i] = (wave * envelope * Short.MAX_VALUE * 0.90).toInt().toShort()
        }
        buffer
    }

    init {
        ensureVolumeUp()
    }

    fun ensureVolumeUp() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            audioManager?.let { am ->
                val maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                val curVol = am.getStreamVolume(AudioManager.STREAM_MUSIC)
                if (curVol < (maxVol * 0.5)) {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, (maxVol * 0.85).toInt(), 0)
                }
            }
        } catch (_: Throwable) {}
    }

    fun saveElevenLabsConfig(apiKey: String, voiceId: String) {
        val cleanKey = apiKey.trim()
        val cleanVoice = if (voiceId.isBlank()) "21m00Tcm4TlvDq8ikWAM" else voiceId.trim()
        prefs.edit()
            .putString("eleven_labs_api_key", cleanKey)
            .putString("eleven_labs_voice_id", cleanVoice)
            .apply()
        _elevenLabsKey.value = cleanKey
        _elevenLabsVoiceId.value = cleanVoice
    }

    /**
     * Resonant bell chime that sounds on page navigation or splash entry.
     * Uses layered playback: plays R.raw.bell_chime and software AudioTrack for crisp resonance.
     */
    fun playPageChime() {
        scope.launch {
            try {
                ensureVolumeUp()
                chimePlayer?.let {
                    try { if (it.isPlaying) it.stop() } catch (_: Exception) {}
                    it.release()
                }
                chimePlayer = null

                var played = false

                // 1. Play software synthesized PCM bell chime for guaranteed hardware audio output
                try {
                    val audioTrack = AudioTrack.Builder()
                        .setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )
                        .setAudioFormat(
                            AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(44100)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build()
                        )
                        .setBufferSizeInBytes(chimePcmBuffer.size * 2)
                        .setTransferMode(AudioTrack.MODE_STATIC)
                        .build()

                    audioTrack.write(chimePcmBuffer, 0, chimePcmBuffer.size)
                    audioTrack.play()

                    delay(1200)
                    try {
                        audioTrack.stop()
                        audioTrack.release()
                    } catch (_: Throwable) {}
                    played = true
                } catch (e: Exception) {
                    Log.w("SoundEngine", "AudioTrack chime exception: ${e.message}")
                }

                // 3. Guaranteed fallback: ToneGenerator
                if (!played) {
                    try {
                        val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
                    } catch (_: Throwable) {}
                }
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error playing chime", e)
            }
        }
    }

    fun playRoomChime(roomType: String) {
        playPageChime()
    }

    fun toggleVoiceIntro() {
        if (_isVoicePlaying.value) {
            pauseVoiceIntro()
        } else {
            playVoiceIntro()
        }
    }

    /**
     * Plays Deana's voice note.
     * Uses openRawResourceFd for guaranteed reliable playback from res/raw.
     */
    fun playVoiceIntro() {
        scope.launch {
            try {
                ensureVolumeUp()
                if (_isMusicPlaying.value) {
                    pauseMusic()
                }

                voicePlayer?.let {
                    try {
                        if (it.isPlaying) it.stop()
                    } catch (_: Exception) {}
                    it.release()
                }
                voicePlayer = null

                // 1. Check for custom ElevenLabs rendered welcome first
                val elevenLabsFile = File(context.cacheDir, "elevenlabs_deana_welcome.mp3")
                var player: MediaPlayer? = null

                if (elevenLabsFile.exists() && elevenLabsFile.length() > 1000L) {
                    try {
                        player = MediaPlayer().apply {
                            setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )
                            setDataSource(elevenLabsFile.absolutePath)
                            setVolume(1.0f, 1.0f)
                            prepare()
                        }
                    } catch (e: Exception) {
                        Log.w("SoundEngine", "Failed to play ElevenLabs file: ${e.message}")
                        player = null
                    }
                }

                // 2. Play the bundled studio master recording using assets
                if (player == null) {
                    try {
                        val afd = context.assets.openFd("dean_intro_voice.mp3")
                        val mp = MediaPlayer().apply {
                            setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                    .build()
                            )
                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                            afd.close()
                            setVolume(1.0f, 1.0f)
                            prepare()
                        }
                        player = mp
                    } catch (e: Exception) {
                        Log.w("SoundEngine", "Asset voice playback error: ${e.message}")
                    }
                }

                if (player != null) {
                    player.setVolume(1.0f, 1.0f)
                    player.setOnCompletionListener {
                        _isVoicePlaying.value = false
                        try {
                            it.release()
                        } catch (_: Exception) {}
                    }
                    player.setOnErrorListener { _, what, extra ->
                        Log.e("SoundEngine", "Voice intro error: what=$what extra=$extra")
                        _isVoicePlaying.value = false
                        true
                    }
                    voicePlayer = player
                    player.start()
                    _isVoicePlaying.value = true
                    Log.i("SoundEngine", "Deana voice intro playing successfully")
                } else {
                    Log.e("SoundEngine", "Could not create MediaPlayer for voice intro")
                    _isVoicePlaying.value = false
                }
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error in playVoiceIntro", e)
                _isVoicePlaying.value = false
            }
        }
    }

    fun pauseVoiceIntro() {
        try {
            voicePlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                }
            }
        } catch (e: Exception) {
            Log.e("SoundEngine", "Error pausing voice intro", e)
        } finally {
            _isVoicePlaying.value = false
        }
    }

    fun synthesizeWithElevenLabs(
        text: String,
        customVoiceId: String? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val apiKey = _elevenLabsKey.value
        if (apiKey.isBlank()) {
            onError("Please enter your ElevenLabs API Key in Audio Settings")
            return
        }

        val voiceId = customVoiceId?.ifBlank { null } ?: _elevenLabsVoiceId.value

        scope.launch {
            _isSynthesizing.value = true
            try {
                val url = URL("https://api.elevenlabs.io/v1/text-to-speech/$voiceId")
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    doOutput = true
                    connectTimeout = 15000
                    readTimeout = 25000
                    setRequestProperty("xi-api-key", apiKey)
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Accept", "audio/mpeg")
                }

                val jsonBody = JSONObject().apply {
                    put("text", text)
                    put("model_id", "eleven_turbo_v2_5")
                    put("voice_settings", JSONObject().apply {
                        put("stability", 0.75)
                        put("similarity_boost", 0.85)
                    })
                }

                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonBody.toString())
                    writer.flush()
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val outputFile = File(context.cacheDir, "elevenlabs_output.mp3")
                    connection.inputStream.use { input ->
                        outputFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    if (text.contains("Hamilton", ignoreCase = true) || text.contains("Built by D", ignoreCase = true)) {
                        outputFile.copyTo(File(context.cacheDir, "elevenlabs_deana_welcome.mp3"), overwrite = true)
                    }

                    withContext(Dispatchers.Main) {
                        _isSynthesizing.value = false
                        playSynthesizedAudio(outputFile)
                        onSuccess()
                    }
                } else {
                    val errorBody = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "HTTP $responseCode"
                    withContext(Dispatchers.Main) {
                        _isSynthesizing.value = false
                        Log.e("SoundEngine", "ElevenLabs API error: $errorBody")
                        onError("ElevenLabs error ($responseCode): $errorBody")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isSynthesizing.value = false
                    Log.e("SoundEngine", "ElevenLabs request failed", e)
                    onError("Network or request failure: ${e.message}")
                }
            }
        }
    }

    private fun playSynthesizedAudio(audioFile: File) {
        scope.launch {
            try {
                ensureVolumeUp()
                voicePlayer?.let {
                    try {
                        if (it.isPlaying) it.stop()
                    } catch (_: Exception) {}
                    it.release()
                }
                voicePlayer = null

                val player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(audioFile.absolutePath)
                    setVolume(1.0f, 1.0f)
                    setOnCompletionListener {
                        _isVoicePlaying.value = false
                        try {
                            it.release()
                        } catch (_: Exception) {}
                    }
                    setOnErrorListener { _, _, _ ->
                        _isVoicePlaying.value = false
                        true
                    }
                    prepare()
                    start()
                }
                voicePlayer = player
                _isVoicePlaying.value = true
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error playing synthesized audio", e)
                _isVoicePlaying.value = false
            }
        }
    }

    fun toggleMusic() {
        if (_isMusicPlaying.value) {
            pauseMusic()
        } else {
            playMusic()
        }
    }

    fun playMusic() {
        scope.launch {
            try {
                ensureVolumeUp()
                if (voicePlayer?.isPlaying == true) {
                    pauseVoiceIntro()
                }
                if (musicPlayer == null) {
                    val afd = try {
                        context.assets.openFd("media/asset-16142e34.mp3")
                    } catch (e: Exception) {
                        null
                    }
                    if (afd != null) {
                        musicPlayer = MediaPlayer().apply {
                            setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )
                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                            afd.close()
                            isLooping = true
                            setVolume(0.6f, 0.6f)
                            prepare()
                        }
                    }
                }
                musicPlayer?.start()
                _isMusicPlaying.value = true
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error playing music", e)
                _isMusicPlaying.value = false
            }
        }
    }

    fun pauseMusic() {
        try {
            musicPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                }
            }
        } catch (e: Exception) {
            Log.e("SoundEngine", "Error pausing music", e)
        } finally {
            _isMusicPlaying.value = false
        }
    }

    fun release() {
        try {
            voicePlayer?.release()
            voicePlayer = null
            chimePlayer?.release()
            chimePlayer = null
            musicPlayer?.release()
            musicPlayer = null
        } catch (e: Exception) {
            Log.e("SoundEngine", "Error releasing sound engine", e)
        }
    }
}
