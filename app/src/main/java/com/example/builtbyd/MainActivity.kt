package com.example.builtbyd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.builtbyd.model.ScreenRoute
import com.example.builtbyd.ui.components.AppTopBar
import com.example.builtbyd.ui.components.ConsultationDialog
import com.example.builtbyd.ui.components.WelcomeSplashScreen
import com.example.builtbyd.ui.screens.DesignRoomScreen
import com.example.builtbyd.ui.screens.HomeScreen
import com.example.builtbyd.ui.screens.HouseFitnessScreen
import com.example.builtbyd.ui.screens.JournalScreen
import com.example.builtbyd.ui.screens.SocialRoomScreen
import com.example.builtbyd.ui.screens.YogaRoomScreen
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltByDTheme
import com.example.builtbyd.viewmodel.BuiltByDViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: BuiltByDViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BuiltByDTheme {
                MainApp(viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: BuiltByDViewModel) {
    val context = LocalContext.current
    val currentRoute by viewModel.currentRoute.collectAsState()
    val showSplash by viewModel.showSplash.collectAsState()
    val isVoicePlaying by viewModel.soundEngine.isVoicePlaying.collectAsState()
    val isConsultationOpen by viewModel.isConsultationOpen.collectAsState()
    val intakeStep by viewModel.intakeStep.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val step1Error by viewModel.step1Error.collectAsState()
    val portal3DUrl by viewModel.portal3DUrl.collectAsState()
    val journalCategory by viewModel.journalCategory.collectAsState()
    val selectedArticleId by viewModel.selectedArticleId.collectAsState()
    val isTestimonialsPaused by viewModel.isTestimonialsPaused.collectAsState()

    // Handle back button behavior
    BackHandler(enabled = isConsultationOpen || currentRoute != ScreenRoute.HOME) {
        if (isConsultationOpen) {
            viewModel.closeConsultation()
        } else {
            viewModel.navigateBack()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(BuiltBlack)) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentRoute = currentRoute,
                    isVoicePlaying = isVoicePlaying,
                    onToggleVoice = { viewModel.soundEngine.toggleVoiceIntro() },
                    onNavigate = { viewModel.navigateTo(it) },
                    onOpenConsultation = { viewModel.openConsultation() }
                )
            },
            containerColor = BuiltBlack,
            modifier = Modifier.testTag("main_scaffold")
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentRoute) {
                    ScreenRoute.HOME -> {
                        HomeScreen(
                            isVoicePlaying = isVoicePlaying,
                            onToggleVoice = { viewModel.soundEngine.toggleVoiceIntro() },
                            onOpenConsultation = { viewModel.openConsultation(it) },
                            onNavigate = { viewModel.navigateTo(it) },
                            isTestimonialsPaused = isTestimonialsPaused,
                            onToggleTestimonials = { viewModel.toggleTestimonialsPause() }
                        )
                    }
                    ScreenRoute.JOURNAL -> {
                        JournalScreen(
                            selectedCategory = journalCategory,
                            onCategorySelected = { viewModel.setJournalCategory(it) },
                            selectedArticleId = selectedArticleId,
                            onArticleSelected = { viewModel.setSelectedArticle(it) },
                            onOpenConsultation = { viewModel.openConsultation() },
                            onBackToHome = { viewModel.navigateTo(ScreenRoute.HOME) }
                        )
                    }
                    ScreenRoute.YOGA -> {
                        YogaRoomScreen(
                            onOpenConsultation = { viewModel.openConsultation() },
                            onBackToHome = { viewModel.navigateTo(ScreenRoute.HOME) }
                        )
                    }
                    ScreenRoute.HOUSE_3D -> {
                        HouseFitnessScreen(
                            portalUrl = portal3DUrl,
                            onUpdatePortalUrl = { viewModel.set3DUrl(it) },
                            onOpenConsultation = { viewModel.openConsultation() },
                            onBackToHome = { viewModel.navigateTo(ScreenRoute.HOME) }
                        )
                    }
                    ScreenRoute.SOCIAL -> {
                        SocialRoomScreen(
                            onOpenConsultation = { viewModel.openConsultation() },
                            onBackToHome = { viewModel.navigateTo(ScreenRoute.HOME) }
                        )
                    }
                    ScreenRoute.DESIGN -> {
                        DesignRoomScreen(
                            onBackToHome = { viewModel.navigateTo(ScreenRoute.HOME) }
                        )
                    }
                }
            }
        }

        // Multi-Step Consultation Dialog
        ConsultationDialog(
            isOpen = isConsultationOpen,
            currentStep = intakeStep,
            formState = formState,
            step1Error = step1Error,
            onClose = { viewModel.closeConsultation() },
            onToggleGoal = { viewModel.toggleGoal(it) },
            onUpdateService = { viewModel.updateService(it) },
            onUpdateExperience = { viewModel.updateExperience(it) },
            onUpdateAgeRange = { viewModel.updateAgeRange(it) },
            onUpdateDays = { viewModel.updateDays(it) },
            onUpdateSuccess = { viewModel.updateSuccess(it) },
            onUpdateFirstName = { viewModel.updateFirstName(it) },
            onUpdateLastName = { viewModel.updateLastName(it) },
            onUpdateEmail = { viewModel.updateEmail(it) },
            onUpdateSocial = { viewModel.updateSocialHandle(it) },
            onUpdateNotes = { viewModel.updateNotes(it) },
            onUpdateConsent = { viewModel.updateConsent(it) },
            onNextStep = { viewModel.nextIntakeStep() },
            onPrevStep = { viewModel.prevIntakeStep() },
            onSendEmail = { viewModel.sendEmailSummary(context) }
        )

        // Welcome Splash Overlay on first launch
        WelcomeSplashScreen(
            visible = showSplash,
            onDismiss = { viewModel.dismissSplash(playVoice = true) }
        )
    }
}
