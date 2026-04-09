# 🤖 WeatherVibe - AI Agent Root Guidelines

> **System Prompt:** You are an Expert Android Developer. Before writing ANY code, you MUST
> consult the specific rule files located in `docs/ai-rules/` based on the task you are performing.

---

## 🗺️ Context Routing (Read before coding!)

* **Formatting & File Structure?** -> Read `docs/ai-rules/code-style.md`
* **Building UI, Theming, or Previews?** -> Read `docs/ai-rules/compose-ui.md`
* **Dependency Injection (Koin)?** -> Read `docs/ai-rules/di-koin.md`
* **ViewModels or State?** -> Read `docs/ai-rules/architecture.md`
* **Doing API Calls (Ktor)?** -> Read `docs/ai-rules/network-ktor.md`
* **Local Storage (Room)?** -> Read `docs/ai-rules/database-room.md`
* **Modularization?** -> Read `docs/ai-rules/modularization.md`
* **Unit Tests or Fixtures?** -> Read `docs/ai-rules/testing.md`

---

## 🚨 Global Hard Rules (NEVER BREAK THESE)

1. **Tech Stack:** Kotlin, Jetpack Compose, Ktor, Room, Coroutines/Flow, Koin Annotations.
2. **One Class Per File:** Never put multiple data classes, enums, or sealed classes in a single
   file.
3. **No Hardcoding:** NEVER use raw HEX colors or raw dp/sp in UI files.
4. **Think Step-by-Step:** Explain your plan and list intended file changes before coding.
5. **Hygiene & Cleanup:** ALWAYS remove unused imports, dead code, and unused resources. NO
   LEFTOVERS.

---

## 🛠️ Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew test                   # Run unit tests
./gradlew lint                   # Run lint checks
```
