package com.example.builtbyd.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.builtbyd.audio.SoundEngine
import com.example.builtbyd.model.ArticleCategory
import com.example.builtbyd.model.ConsultationForm
import com.example.builtbyd.model.ScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuiltByDViewModel(application: Application) : AndroidViewModel(application) {

    val soundEngine = SoundEngine(application.applicationContext)

    private val _currentRoute = MutableStateFlow(ScreenRoute.HOME)
    val currentRoute: StateFlow<ScreenRoute> = _currentRoute.asStateFlow()

    private val navStack = mutableListOf<ScreenRoute>()

    private val _showSplash = MutableStateFlow(false)
    val showSplash: StateFlow<Boolean> = _showSplash.asStateFlow()

    private val _isConsultationOpen = MutableStateFlow(false)
    val isConsultationOpen: StateFlow<Boolean> = _isConsultationOpen.asStateFlow()

    private val _intakeStep = MutableStateFlow(1)
    val intakeStep: StateFlow<Int> = _intakeStep.asStateFlow()

    private val _formState = MutableStateFlow(ConsultationForm())
    val formState: StateFlow<ConsultationForm> = _formState.asStateFlow()

    private val _step1Error = MutableStateFlow<String?>(null)
    val step1Error: StateFlow<String?> = _step1Error.asStateFlow()

    private val _portal3DUrl = MutableStateFlow("https://spatial.io/s/Deanas-House-Fitness")
    val portal3DUrl: StateFlow<String> = _portal3DUrl.asStateFlow()

    private val _journalCategory = MutableStateFlow(ArticleCategory.ALL)
    val journalCategory: StateFlow<ArticleCategory> = _journalCategory.asStateFlow()

    private val _selectedArticleId = MutableStateFlow<String?>(null)
    val selectedArticleId: StateFlow<String?> = _selectedArticleId.asStateFlow()

    private val _isTestimonialsPaused = MutableStateFlow(false)
    val isTestimonialsPaused: StateFlow<Boolean> = _isTestimonialsPaused.asStateFlow()

    fun dismissSplash(playVoice: Boolean = true) {
        _showSplash.value = false
        if (playVoice) {
            soundEngine.playVoiceIntro()
        }
    }

    fun navigateTo(route: ScreenRoute) {
        if (_currentRoute.value != route) {
            navStack.add(_currentRoute.value)
            _currentRoute.value = route
            soundEngine.playRoomChime(
                when (route) {
                    ScreenRoute.HOUSE_3D -> "house"
                    ScreenRoute.YOGA -> "yoga"
                    ScreenRoute.JOURNAL -> "journal"
                    ScreenRoute.SOCIAL -> "social"
                    ScreenRoute.DESIGN -> "design"
                    else -> "home"
                }
            )
        }
    }

    fun navigateBack(): Boolean {
        if (navStack.isNotEmpty()) {
            _currentRoute.value = navStack.removeAt(navStack.lastIndex)
            return true
        }
        return false
    }

    fun openConsultation(servicePrefill: String? = null) {
        if (!servicePrefill.isNullOrBlank()) {
            _formState.value = _formState.value.copy(service = servicePrefill)
        }
        _intakeStep.value = 1
        _isConsultationOpen.value = true
    }

    fun closeConsultation() {
        _isConsultationOpen.value = false
    }

    fun toggleGoal(goal: String) {
        val currentGoals = _formState.value.selectedGoals.toMutableSet()
        if (currentGoals.contains(goal)) {
            currentGoals.remove(goal)
        } else {
            currentGoals.add(goal)
        }
        _formState.value = _formState.value.copy(selectedGoals = currentGoals)
        if (currentGoals.isNotEmpty()) {
            _step1Error.value = null
        }
    }

    fun updateService(service: String) {
        _formState.value = _formState.value.copy(service = service)
    }

    fun updateExperience(experience: String) {
        _formState.value = _formState.value.copy(experience = experience)
    }

    fun updateAgeRange(ageRange: String) {
        _formState.value = _formState.value.copy(ageRange = ageRange)
    }

    fun updateDays(days: String) {
        _formState.value = _formState.value.copy(daysAvailable = days)
    }

    fun updateSuccess(success: String) {
        _formState.value = _formState.value.copy(success90Days = success)
    }

    fun updateFirstName(name: String) {
        _formState.value = _formState.value.copy(firstName = name)
    }

    fun updateLastName(name: String) {
        _formState.value = _formState.value.copy(lastName = name)
    }

    fun updateEmail(email: String) {
        _formState.value = _formState.value.copy(email = email)
    }

    fun updateSocialHandle(handle: String) {
        _formState.value = _formState.value.copy(socialHandle = handle)
    }

    fun updateNotes(notes: String) {
        _formState.value = _formState.value.copy(notes = notes)
    }

    fun updateConsent(consent: Boolean) {
        _formState.value = _formState.value.copy(consent = consent)
    }

    fun nextIntakeStep(): Boolean {
        when (_intakeStep.value) {
            1 -> {
                if (_formState.value.selectedGoals.isEmpty()) {
                    _step1Error.value = "Choose at least one goal to continue."
                    return false
                }
                _step1Error.value = null
                _intakeStep.value = 2
                return true
            }
            2 -> {
                if (_formState.value.isStep2Valid) {
                    _intakeStep.value = 3
                    return true
                }
                return false
            }
            3 -> {
                if (_formState.value.isStep3Valid) {
                    _intakeStep.value = 4 // Success step
                    return true
                }
                return false
            }
        }
        return false
    }

    fun prevIntakeStep() {
        if (_intakeStep.value > 1) {
            _intakeStep.value = _intakeStep.value - 1
        }
    }

    fun set3DUrl(url: String) {
        if (url.isNotBlank()) {
            _portal3DUrl.value = url.trim()
        }
    }

    fun setJournalCategory(category: ArticleCategory) {
        _journalCategory.value = category
    }

    fun setSelectedArticle(articleId: String?) {
        _selectedArticleId.value = articleId
    }

    fun toggleTestimonialsPause() {
        _isTestimonialsPaused.value = !_isTestimonialsPaused.value
    }

    fun sendEmailSummary(context: Context) {
        val brief = _formState.value.generateBrief()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:deanambrady@hotmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "Built by D consultation request")
            putExtra(Intent.EXTRA_TEXT, brief)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No email app found. Brief copied to clipboard.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundEngine.release()
    }
}
