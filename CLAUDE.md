# 🤖 WeatherVibe - AI Agent Root Guidelines

> **System Prompt:** You are an Expert Android Developer. Before writing ANY code, you MUST
> consult the specific rule files located in `docs/ai-rules/` based on the task you are performing.

---

## 🗺️ Context Routing (Read before coding!)
* **Formatting & File Structure?** -> Read `docs/ai-rules/code-style.md`
* **Building UI, Theming, or parsing Figma?** -> Read `docs/ai-rules/compose-ui.md`
* **Adding ViewModels or State?** -> Read `docs/ai-rules/architecture.md`
* **Doing API Calls (Ktor)?** -> Read `docs/ai-rules/network-ktor.md`
* **Local Storage (Room)?** -> Read `docs/ai-rules/database-room.md`

---

## 🚨 Global Hard Rules (NEVER BREAK THESE)
1. **Tech Stack:** Kotlin 2.1.21, Jetpack Compose, Ktor, Room, Coroutines/Flow, Koin Annotations.
2. **One Class Per File:** Never put multiple data classes, enums, or sealed classes in a single file.
3. **No Hardcoding:** NEVER use raw HEX colors or raw dp/sp in UI files.
4. **Think Step-by-Step:** Explain your plan and list intended file changes before coding.
5. **Hygiene & Cleanup:** ALWAYS remove unused imports, dead code, and unused resources. NO LEFTOVERS.

---

## 🔒 Scope Discipline (BEFORE & AFTER every task)

### BEFORE writing code:
1. **Read the rules.** Open the relevant `docs/ai-rules/` file for the task at hand. Do not rely
   on memory - re-read every time.
2. **Study existing patterns.** Before creating or refactoring, read the surrounding code first.
   Follow the conventions already established in the codebase - do not invent new patterns when
   one already exists.
3. **Scope lock.** Do ONLY what was requested. If the user asks to change one property in a class,
   do not remove, rename, or restructure anything else in the file. When in doubt about scope,
   ask - do not assume.

### AFTER writing code:
1. **Scope check.** Diff your changes against the request. Did you remove, rename, or add anything
   that was NOT explicitly asked for? If yes - revert that part.
2. **Pattern check.** Do your changes follow the patterns described in `docs/ai-rules/` and already
   present in the codebase? Specifically verify: Resources wrapper, Factory injection, naming
   conventions.
3. **Run the Self-Verification Checklist** from the relevant `docs/ai-rules/` file.

---

## 🛠️ Build Commands
```bash
./gradlew assembleDebug          # Build debug APK
./gradlew test                   # Run unit tests
./gradlew lint                   # Run lint checks
```
