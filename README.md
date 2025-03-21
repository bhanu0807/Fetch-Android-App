# Fetch Rewards App

## Overview
The Fetch Rewards App is a native Android application developed in Kotlin as part of the Fetch Rewards coding exercise. It focuses on efficiency, user-friendliness, and maintainability, following best industry practices.

## Features
- Native Android app built with Kotlin
- Efficient and optimized architecture
- Uses Gradle for dependency management
- Follows MVVM architecture
- Compatible with latest Android versions

## Tech Stack
- Language: Kotlin
- Framework: Android SDK
- Build Tool: Gradle
- Version Control: Git
- IDE: Android Studio

## Project Structure
```
fetch-rewards-app/
├── app/                    # Main application source code
├── gradle/                 # Gradle wrapper files
├── build.gradle.kts        # Project-level Gradle settings
├── settings.gradle.kts     # Gradle module settings
├── LICENSE                 # License information
└── README.md               # Project documentation
```

## Prerequisites
Ensure you have the following installed before setting up the project:
- Android Studio (Latest version recommended)
- Java Development Kit (JDK 11 or later)
- Gradle (Bundled with Android Studio)
- Git (For version control)

## Installation & Setup
### Clone the Repository
```sh
git clone https://github.com/bhanu0807/fetch-android-app.git
cd fetch-rewards-app
```

### Open in Android Studio
1. Launch Android Studio
2. Click "Open an Existing Project"
3. Select the `fetch-rewards-app` directory
4. Let Gradle sync dependencies

### Configure Local Properties
If required, create a `local.properties` file inside the root directory and add:
```properties
sdk.dir=/path/to/your/android/sdk
```

### Build & Run the App
- Select a connected device or emulator
- Click "Run" in Android Studio
- Or run via command line:
```sh
./gradlew assembleDebug
```

### Screenshots

![Screenshot 2025-03-19 235005](https://github.com/user-attachments/assets/d8c18dd8-37a1-4777-8c01-cd202786cdbe) ![Screenshot 2025-03-19 235024](https://github.com/user-attachments/assets/fc0b6cd7-49a5-47cb-a59d-648b85a18763) ![Screenshot 2025-03-19 235110](https://github.com/user-attachments/assets/b61cc477-f4a7-4e1e-a256-83d268ef1cd2) ![Screenshot 2025-03-19 235138](https://github.com/user-attachments/assets/7e976a89-e2f9-422b-9902-d10c331eb72a)






## Workflow
1. Clone the repository to get the latest code.
2. Open the project in Android Studio.
3. Sync dependencies using Gradle.
4. Build the project.
5. Run it on an emulator or a physical device.

## Troubleshooting
- If Gradle sync fails, try running "File > Invalidate Caches & Restart" in Android Studio.
- If the Android SDK is missing, ensure the correct SDK path is set in `local.properties`.
- If the build fails, try running `./gradlew clean` before rebuilding.

---
GitHub Repository: [Fetch Rewards App](https://github.com/bhanu0807/fetch-android-app.git)
