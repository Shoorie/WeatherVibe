# 📦 Gradle Modularization & Namespace Strategy (CRITICAL RULES)

> **Core Principle:** This project strictly enforces a **Namespace-Driven, Layered Feature**
> **Architecture**. You MUST group modules into physical directories that match their Gradle
> namespace.

## 📋 Table of Contents
1. [Directory Separation (Keep Root Clean)](#1-directory-separation-keep-root-clean)
2. [Package Naming Convention](#2-package-naming-convention)
3. [Module Types & Structure (The Matrix)](#3-module-types--structure-the-matrix)
4. [Strict Dependency Flow (DO NOT BREAK)](#4-strict-dependency-flow-do-not-break)
5. [Internal Package Structure (CRITICAL)](#5-internal-package-structure-critical)
6. [Module Creation Rules](#6-module-creation-rules)

---

## 1. Directory Separation (Keep Root Clean)
* **FORBIDDEN:** Do not mix source code modules with project configuration files in the root
  directory.
* Physical folders like `domain/`, `data/`, `feature/`, and `core/` act as organizational root
  folders for the modules.
* Example: A module with the namespace `:domain:profile` MUST live in the physical directory
  `domain/profile/`.

---

## 2. Package Naming Convention
* The Kotlin package name MUST perfectly mirror the module's namespace to ensure easy navigation.
* **Format:** `com.[company].[appname].[layer].[feature_name]`
* Example for `:feature:search`: `package com.example.myapp.feature.search`
* Example for `:domain:profile`: `package com.example.myapp.domain.profile`

---

## 3. Module Types & Structure (The Matrix)
You MUST categorize every new module into one of the following groups:

### A. `:app` (The Assembler)
* **Location:** `app/`
* **Role:** Only contains the Application class, global DI initialization, and the root Navigation
  Graph mapping.

### B. `:core:[name]` (Shared Infrastructure)
* **Location:** `core/[name]/` (e.g., `core/network`, `core/designsystem`, `core/database`)
* **Role:** Global, feature-agnostic tools.
* **Rule:** Core modules MUST NOT depend on `:domain`, `:data`, or `:feature` modules.

### C. `:domain:[feature]` (Pure Business Logic)
* **Location:** `domain/[feature]/` (e.g., `domain/search`, `domain/profile`)
* **Role:** Contains pure Kotlin code. Models, Repository Interfaces, and Use Cases.
* **Rule:** MUST NOT contain any Android dependencies (no Context, no Compose).

### D. `:data:[feature]` (Implementation & Sources)
* **Location:** `data/[feature]/` (e.g., `data/search`, `data/profile`)
* **Role:** Contains API implementations (Ktor), DTOs, Room Entities, DAOs, and Repository
  implementations.
* **Rule:** MUST implement interfaces defined in its respective `:domain:[feature]` module.

### E. `:feature:[feature]` (UI & Presentation)
* **Location:** `feature/[feature]/` (e.g., `feature/search`, `feature/profile`)
* **Role:** Contains ViewModels, UI State, and Jetpack Compose screens.
* **Rule:** MUST NOT contain any network DTOs or database entities. Uses models from
  `:domain:[feature]`.

---

## 4. Strict Dependency Flow (DO NOT BREAK)
To prevent circular dependencies and maintain build speed, follow these rules:

1. `:app` depends on ALL `:feature`, `:data`, and `:core` modules.
2. `:feature:[name]` depends on `:domain:[name]` and `:core:designsystem`.
3. `:data:[name]` depends on `:domain:[name]`, `:core:network`, and `:core:database`.
4. **FORBIDDEN:** A `:feature` module MUST NEVER depend on another `:feature` module.
   (Use deep links or route registries in `:app`).
5. **FORBIDDEN:** A `:domain` module MUST NEVER depend on a `:data` or `:feature` module.
6. **FORBIDDEN:** A `:data` module MUST NEVER depend on a `:feature` module.

---

## 5. Internal Package Structure (CRITICAL)
Creating the module is only half the job. Inside each module's
`src/main/kotlin/com/[company]/[app]/[layer]/[feature]/` directory, you MUST organize files into
specific sub-packages based on their responsibility.

**FORBIDDEN:** NEVER dump all files into the root package of the module.

### A. `:domain:[feature]` Internal Packages:
* `.model` - Pure Kotlin data classes representing domain concepts.
* `.repository` - Interfaces defining data operations.
* `.usecase` - Classes encapsulating single business rules.
* `.error` - Custom domain exceptions or error sealed classes.

### B. `:data:[feature]` Internal Packages:
* `.remote.dto` - Network response models (Ktor `@Serializable`).
* `.remote.api` - Ktor network clients/services.
* `.local.entity` - Room database entities (`@Entity`).
* `.local.dao` - Room Data Access Objects (`@Dao`).
* `.repository` - Implementations of the domain repository interfaces.
* `.mapper` - Extension functions converting DTOs/Entities to Domain models
  (e.g., `SearchResultsToDomain.kt`).

### C. `:feature:[feature]` Internal Packages:
* `.presentation` - ViewModels, UI States (sealed interfaces), and UI Events.
* `.ui.screen` - The main stateful and stateless screen Composables.
* `.ui.component` - Small, reusable UI elements specific to this feature (can be grouped
  further, e.g., `.ui.component.header`).
* `.navigation` - NavGraph builder extensions and route objects.

---

## 6. Module Creation Rules
When asked to "create a module":
1. Create the physical directory structure matching the namespace
   (e.g., `mkdir -p data/profile/src/main/kotlin/...`).
2. Add a `build.gradle.kts` using Kotlin DSL.
3. Register the namespace in the root `settings.gradle.kts` (e.g., `include(":data:profile")`).
4. Declare dependencies using Version Catalogs (`libs.xxx`).
