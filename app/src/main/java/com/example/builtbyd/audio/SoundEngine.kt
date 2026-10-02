package com.example.builtbyd.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

class SoundEngine(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var voicePlayer: MediaPlayer? = null
    private var musicPlayer: MediaPlayer? = null

    private val _isVoicePlaying = MutableStateFlow(false)
    val isVoicePlaying: StateFlow<Boolean> = _isVoicePlaying.asStateFlow()

    private val _isMusicPlaying = MutableStateFlow(false)
    val isMusicPlaying: StateFlow<Boolean> = _isMusicPlaying.asStateFlow()

    fun toggleVoiceIntro() {
        if (_isVoicePlaying.value) {
            pauseVoiceIntro()
        } else {
            playVoiceIntro()
        }
    }

    fun playVoiceIntro() {
        scope.launch {
            try {
                if (_isMusicPlaying.value) {
                    pauseMusic()
                }

                voicePlayer?.release()
                val afd = try {
                    context.assets.openFd("dean_intro_voice.mp3")
                } catch (e: Exception) {
                    try {
                        context.assets.openFd("assets/media-05-aea198b054.mp3")
                    } catch (e2: Exception) {
                        null
                    }
                }

                if (afd != null) {
                    voicePlayer = MediaPlayer().apply {
                        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                        afd.close()
                        setOnCompletionListener {
                            _isVoicePlaying.value = false
                        }
                        setOnErrorListener { _, _, _ ->
                            _isVoicePlaying.value = false
                            true
                        }
                        prepare()
                        start()
                    }
                    _isVoicePlaying.value = true
                } else {
                    Log.w("SoundEngine", "Voice intro asset not found")
                }
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error playing voice intro", e)
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
                            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                            afd.close()
                            isLooping = true
                            setVolume(0.5f, 0.5f)
                            prepare()
                        }
                    }
                }
                musicPlayer?.start()
                _isMusicPlaying.value = true
            } catch (e: Exception) {
                Log.e("SoundEngine", "Error playing background music", e)
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
            Log.e("SoundEngine", "Error pausing background music", e)
        } finally {
            _isMusicPlaying.value = false
        }
    }

    /**
     * Synthesizes and plays the signature acoustic resonant chime for each room,
     * matching the harmonic frequencies from the web app.
     */
    fun playRoomChime(roomType: String) {
        scope.launch {
            try {
                val sampleRate = 44100
                val durationSec = 1.6
                val totalSamples = (sampleRate * durationSec).toInt()

                // Harmonics matching the web app:
                val freqs = when (roomType) {
                    "house" -> doubleArrayOf(440.0, 554.37, 659.25, 880.0)
                    "yoga" -> doubleArrayOf(528.0, 792.0, 1056.0) // 528Hz Solfeggio / singing bowl
                    "journal" -> doubleArrayOf(659.25, 830.61, 987.77)
                    "social" -> doubleArrayOf(587.33, 739.99, 932.33, 1174.66)
                    else -> doubleArrayOf(329.63, 493.88, 659.25)
                }

                val buffer = ShortArray(totalSamples)
                for (i in 0 until totalSamples) {
                    val t = i.toDouble() / sampleRate
                    var sampleVal = 0.0
                    for (f in freqs) {
                        val decay = exp(-2.8 * t)
                        sampleVal += sin(2.0 * PI * f * t) * decay
                    }
                    val normalized = (sampleVal / freqs.size).coerceIn(-1.0, 1.0)
                    buffer[i] = (normalized * 24000).toInt().toShort()
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
                scope.launch {
                    kotlinx.coroutines.delay((durationSec * 1000).toLong() + 200)
                    track.release()
                }
            } catch (e: Exception) {
                // Audio synthesis optional, fails gracefully
            }
        }
    }

    fun release() {
        voicePlayer?.release()
        musicPlayer?.release()
        voicePlayer = null
        musicPlayer = null
    }
}
