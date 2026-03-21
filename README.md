# 🌤️ VibeWeather

<div align="center">

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-4285F4.svg?logo=android)](https://developer.android.com/jetpack/compose)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean_&_MVI-success.svg)](#)
[![Vibe Coding](https://img.shields.io/badge/Built_with-AI_&_Vibe_Coding-8A2BE2.svg)](#)

**A beautifully crafted, highly modular Android weather application.** *Built to demonstrate strict architectural discipline and the power of AI-assisted development.*

  <br />

  <img src="art/demo.gif" alt="VibeWeather App Demo" width="300" />

</div>

## 📖 About The Project

**VibeWeather** is more than just another weather app. It's an experimental playground exploring
how **"Vibe Coding"** (human-AI pair programming) can produce enterprise-grade, scalable software
when constrained by unbreakable architectural rules.

Instead of letting AI hallucinate spaghetti code, this project is governed by a strict set of
developer-defined rules. The result? A perfectly structured, highly modular Android application
that feels both modern and robust.

### ✨ Key Features
* **Real-time Weather Data:** Accurate forecasting powered by Open-Meteo API.
* **Token-Based Design System:** A completely custom, resource-injected UI layer with zero
  hardcoded colors or dimensions.
* **Offline Support:** Robust local caching using Room database with relational mapping.
* **Fluid Animations:** Smooth transitions and micro-interactions built with Jetpack Compose.

---

## 🏗️ Architecture & Tech Stack

This project is a love letter to **Clean Architecture** and **Unidirectional Data Flow (UDF)**.
It strictly separates business logic from presentation, ensuring maximum testability.

### The Stack
* **UI:** Jetpack Compose (Strict Stateless/Stateful separation)
* **Architecture:** MVI (Model-View-Intent) with strictly **Passive ViewModels**.
* **Network:** Ktor Client + `kotlinx.serialization` (No Retrofit!).
* **Local Storage:** Room Database (Relational schemas, no flat JSON dumps).
* **Dependency Injection:** Koin (using KSP `@KoinViewModel` and `@Single` annotations).
* **Asynchronous Programming:** Kotlin Coroutines & Flow (using `Flow.catch` safety patterns).

### Modularization Strategy
The project is sliced by features and layered by architecture into physical directories:
* `:app` — The assembler and navigation hub.
* `:core:designsystem` - Theme tokens, colors, typography.
* `:domain:[feature]` - Pure Kotlin business logic and Use Cases.
* `:data:[feature]` - DTOs, Entities, and Repository implementations.
* `:feature:[feature]` - Composables, ViewModels, and UI States.

---

## 🤖 The "Vibe Coding" Manifesto

This project was built using AI assistants natively within Android Studio (via Claude), but under
a strict **Developer's Constitution**. Before generating any code, the AI agent is forced to read
our `CLAUDE.md` root prompt, which enforces high-level architectural discipline.

### The Constitution Files (Our AI Rules):
Dive into the exact rules that guide the AI in this project:
* 🏗️ [Architecture & State](docs/ai-rules/architecture.md) - Strict UDF and Passive ViewModels.
* 🎨 [Compose UI Guidelines](docs/ai-rules/compose-ui.md) - Token-based theming and Stateless UI.
* 🌐 [Network & Ktor](docs/ai-rules/network-ktor.md) - Zero DTO leakage and safe Flow mapping.
* 💾 [Database & Room](docs/ai-rules/database-room.md) - Pure relational schemas.
* 💅 [Code Style](docs/ai-rules/code-style.md) - 2-space indents, clean imports, strict formatting.
* 📦 [Modularization](docs/ai-rules/modularization.md) - Namespace-driven directory structures.

---

## 🚀 Getting Started

### Prerequisites
* **Android Studio:** Ladybug Feature Drop (or newer)
* **JDK:** Version 17+

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/yourusername/VibeWeather.git](https://github.com/yourusername/VibeWeather.git)
   ```
2. Open the project in Android Studio.
3. Build and run the app:
   ```bash
   ./gradlew assembleDebug
   ```

---

<div align="center">
  <b>Crafted with ❤️ and 🤖</b>
  <br/>
  <i>Feel free to use this architecture as inspiration for your own projects!</i>
</div>
