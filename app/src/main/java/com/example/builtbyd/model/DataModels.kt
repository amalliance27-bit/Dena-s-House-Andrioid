package com.example.builtbyd.model

enum class ScreenRoute(val title: String) {
    HOME("Home"),
    JOURNAL("Journal"),
    YOGA("Yoga & Mobility"),
    HOUSE_3D("House Fitness 3D"),
    SOCIAL("Social Room"),
    DESIGN("Design Room")
}

data class CoachingService(
    val id: String,
    val title: String,
    val tag: String,
    val description: String,
    val imageAsset: String? = null,
    val isPrimary: Boolean = false
)

data class Principle(
    val number: String,
    val title: String,
    val description: String
)

enum class ArticleCategory(val displayName: String) {
    ALL("All"),
    TRAINING("Training"),
    NUTRITION("Nutrition"),
    WOMEN("Women"),
    MINDSET("Mindset")
}

data class JournalArticle(
    val id: String,
    val category: ArticleCategory,
    val number: String,
    val readTime: String,
    val title: String,
    val takeaway: String,
    val answers: List<String>,
    val imageAsset: String? = null
)

data class Testimonial(
    val quote: String,
    val author: String,
    val location: String,
    val isSample: Boolean = false
)

data class YogaPillar(
    val step: String,
    val label: String,
    val title: String,
    val description: String,
    val imageAsset: String
)

data class VirtualHouseZone(
    val zoneNumber: String,
    val title: String,
    val description: String
)

data class SocialChannel(
    val name: String,
    val handle: String,
    val description: String,
    val url: String,
    val iconKey: String
)

data class SocialFeedItem(
    val tag: String,
    val quote: String,
    val imageAsset: String
)

data class ConsultationForm(
    val selectedGoals: Set<String> = emptySet(),
    val service: String = "",
    val experience: String = "",
    val ageRange: String = "",
    val daysAvailable: String = "",
    val success90Days: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val socialHandle: String = "",
    val notes: String = "",
    val consent: Boolean = false
) {
    val isStep1Valid: Boolean get() = selectedGoals.isNotEmpty()
    val isStep2Valid: Boolean get() = service.isNotBlank() && experience.isNotBlank() && ageRange.isNotBlank() && daysAvailable.isNotBlank() && success90Days.isNotBlank()
    val isStep3Valid: Boolean get() = firstName.isNotBlank() && lastName.isNotBlank() && email.contains("@") && consent

    fun generateBrief(): String {
        return buildString {
            appendLine("PRECISION PHYSIQUE — BUILT BY D")
            appendLine("CONSULTATION INTAKE")
            appendLine()
            appendLine("Name: $firstName $lastName".trim())
            appendLine("Email: $email")
            appendLine("Social handle: ${socialHandle.ifBlank { "None shared" }}")
            appendLine("Primary goals: ${selectedGoals.joinToString(", ")}")
            appendLine("Preferred coaching: $service")
            appendLine("Training experience: $experience")
            appendLine("Age range: $ageRange")
            appendLine("Training availability: $daysAvailable")
            appendLine()
            appendLine("90-day success: $success90Days")
            appendLine("Additional notes: ${notes.ifBlank { "None shared" }}")
        }
    }
}
