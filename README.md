# Precision Physique — Built by D (Android Application)

Precision Physique — Built by D is a modern native Android application written with **Kotlin** and **Jetpack Compose**, based on the training philosophy and community of coach **Deana Brady** in Hamilton, Ontario.

## Features

- **Front Room / Main Dashboard:**
  - Hero introduction with Deana's signature branding ("Stronger at every age. Real training. Real women. Real results.")
  - Integrated Deana Voice Introduction audio player with synchronized playback state and ambient acoustic soundscapes.
  - Core philosophy & 5 Principles of the Built by D method.
  - Complete coaching service offerings (1:1 Personal Training, Online Coaching, Built by D Programs, Women's Strength).
  - Meet your coach (Deana Brady) biography, standards, and photo portrait.
  - Animated goal ribbon marquee ticker.
  - Real client testimonials with pause/play ticker control.

- **The Built by D Journal:**
  - Complete library of 14 in-depth articles across four distinct categories: Training, Nutrition, Women, Mindset.
  - Instant category filtering and search.
  - Expandable detailed answers and takeaways for every article.

- **Yoga & Mobility Room:**
  - Four movement pillars: 01/MOVE, 02/STRENGTH, 03/BREATHE, and 04/CONNECT with beach and outdoor movement imagery.

- **Dean'a House Fitness 3D (Virtual Training Dimension):**
  - Interactive 3D Canvas visualizer with real-time 3D projection of gym equipment (dumbbell and kettlebell), 360-degree drag-to-orbit touch controls, glowing portal rings, and floating particle fields.
  - Metaverse gateway portal launcher with editable and copyable virtual world URLs.
  - 4 specialized training zone showcases (Virtual Power Loft, Recovery Sanctuary, 1-on-1 Avatar Arena, Global Member Pavilion).

- **The Social Media Room:**
  - Channels integration (Instagram, Facebook Community, TikTok, YouTube).
  - Community feed moments & client stories.

- **Design Room:**
  - Dedicated showcase for creative direction and the story behind the build.

- **Interactive Consultation Booking Intake:**
  - 3-step questionnaire (Goals, Coaching Preferences & Availability, Contact Information & Consent).
  - Validations and error handling.
  - Generated consultation brief with copy-to-clipboard and direct email intent dispatch.

## Architecture & Technology Stack

- **Language:** Kotlin 2.2.10
- **UI Framework:** Jetpack Compose with Material Design 3 (M3)
- **Architecture:** MVVM with `AndroidViewModel` and Kotlin Coroutines / `StateFlow`
- **Build System:** Gradle 9.3.1 (Kotlin DSL) with Android Gradle Plugin 9.1.1
- **Image Loading:** Coil Compose
- **Audio & Sound:** Android `MediaPlayer` for voice intro & background music, plus low-level PCM `AudioTrack` acoustic chime synthesis
- **Minimum SDK:** 26 (Android 8.0 Oreo) | **Target & Compile SDK:** 36
