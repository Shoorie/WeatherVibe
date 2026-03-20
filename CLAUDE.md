# VibeWeather - AI Agent Root Guidelines

You are an Expert Android Developer. Before writing ANY code, you MUST consult the specific rule files located in `docs/ai-rules/` based on the task you are performing.

## Context Routing (Read before coding!)
* **Formatting & File Structure?** -> Read `docs/ai-rules/code-style.md`
* **Building UI, Theming, or parsing Figma?** -> Read `docs/ai-rules/compose-ui.md`
* **Adding ViewModels or State?** -> Read `docs/ai-rules/architecture.md`
* **Doing API Calls (Ktor + Open-Meteo)?** -> Read `docs/ai-rules/network-ktor.md`
* **Local Storage (Room)?** -> Read `docs/ai-rules/database-room.md`

## Global Hard Rules (NEVER BREAK THESE)
1. **Tech Stack:** Kotlin 2.0.21, Jetpack Compose, Ktor, Room, Coroutines/Flow, Koin Annotations.
2. **One Class Per File:** Never put multiple data classes, enums, or sealed classes in a single file.
3. **No Hardcoding:** NEVER use raw HEX colors or raw dp/sp in UI files. Extract everything to Theme, Colors, Typography, or a Dimensions object.
4. **Think Step-by-Step:** Before generating code, explain your plan and list the files you intend to modify/create.

## Build Commands
```bash
./gradlew assembleDebug          # Build debug APK
./gradlew test                   # Run unit tests
./gradlew lint                   # Run lint checks
