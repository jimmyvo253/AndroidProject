# FlashCard Learning App

A modern, high-performance Android application designed for bilingual vocabulary mastery. This app allows users to create, manage, and study English-Vietnamese flashcards with integrated AI-powered audio synthesis.

## 🚀 Key Features

- **Dynamic Flashcard Management:** Full CRUD (Create, Read, Update, Delete) capabilities for personalized flashcard decks.
- **Smart Study Mode:** Randomized lesson generation with an interactive card-flipping UI to maximize retention.
- **Audio Synthesis:** Real-time pronunciation generation using cloud-based TTS (Text-to-Speech) integrated with `ExoPlayer`.
- **Intelligent Search:** Advanced filtering options including exact and partial matching for both English and Vietnamese terms.
- **Secure Authentication:** Token-based authentication system with persistent session management.
- **Offline First:** Local data persistence ensures your study materials are always accessible.

## 🛠 Tech Stack

- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (100% Declarative UI)
- **Navigation:** Type-safe Navigation Compose
- **Database:** [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **Local Storage:** Jetpack DataStore (Preferences)
- **Networking:** Retrofit 2 & OkHttp 3
- **Concurrency:** Kotlin Coroutines & Flow
- **Media:** Media3 ExoPlayer for audio playback
- **Dependency Management:** Gradle (Kotlin DSL) with Version Catalogs

## 📦 Installation & Setup

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17+
- Android SDK 34+

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/jimmyvo253/AndroidProject.git
   ```
2. **Open in Android Studio:**
   - Select `File > Open` and navigate to the project folder.
3. **Sync Gradle:**
   - Allow Android Studio to sync dependencies from `libs.versions.toml`.
4. **Build and Run:**
   - Connect an Android device or start an emulator (API 26+ recommended).
   - Press the **Run** button in Android Studio.

## 🏗 Architecture

The project follows a modern Android architectural approach:
- **UI Layer:** Jetpack Compose functions using state hoisting and `remember` for local state management.
- **Data Layer:** 
    - `FlashCardDatabase`: Room implementation for structured local storage.
    - `NetworkService`: Retrofit interface for remote API communication.
    - `DataStore`: Handles lightweight key-value storage for user tokens.
- **Navigation:** Centralized `Navigator.kt` managing type-safe routes and dependency injection of DAOs and Services.

## 📝 Usage

1. **Login:** Enter your email to receive an authentication token.
2. **Add Cards:** Use the "Add Card" screen to input English and Vietnamese word pairs.
3. **Study:** Navigate to the "Study" mode. Tap a card to flip it; if it's the Vietnamese side, the app will automatically fetch and play the pronunciation.
4. **Search:** Use the search feature to find specific cards in your collection using flexible matching rules.

---
*Developed as a comprehensive mobile programming project showcasing modern Android development patterns.*
