# WeatherVibe - Android App

## Project
Weather app built with Clean Architecture, Passive ViewModels, and Fat Domains.

## Tech Stack
Kotlin · Jetpack Compose · Ktor · Room · Coroutines/Flow · Koin Annotations (KSP)

## Build Commands
```bash
./gradlew assembleDebug          # Build debug APK
./gradlew test                   # Run unit tests
./gradlew lint                   # Run lint checks
```

## Hard Rules (always apply)
- One class/interface/sealed class per file. No `Models.kt` or `Common.kt`.
- No hardcoded strings, colors, dp/sp values in code.
- Remove unused imports, dead code, commented-out blocks after every change.
- 2-space indentation, 100-char line limit.
- Constructor parameters sorted alphabetically.

## Coding Tasks
When writing or modifying Kotlin / Compose code, follow the `android-vibe-architect` skill
in `.claude/skills/android-vibe-architect/`.
