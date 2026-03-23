# 💉 Dependency Injection with Koin (CRITICAL RULES)

> **Core Principle:** This project exclusively uses **Koin Annotations (KSP)**. Writing manual
> `module { ... }` blocks is strictly forbidden.

## 📋 Table of Contents

1. [General Setup & KSP](#1-general-setup--ksp)
2. [Annotation Standards per Layer](#2-annotation-standards-per-layer)
3. [Constructor Injection (The Mandatory Way)](#3-constructor-injection-the-mandatory-way)
4. [Handling Interfaces (Binds)](#4-handling-interfaces-binds)
5. [Injected Parameters in UI (Composables)](#5-injected-parameters-in-ui-composables)
6. [Self-Verification Checklist](#6-self-verification-checklist)

---

## 1. General Setup & KSP

* **FORBIDDEN:** Never write manual Koin modules using the `module { ... }` DSL.
* **Mechanism:** All dependencies are detected via `@ComponentScan`.
* **Requirement:** Ensure `@Module` with `@ComponentScan` exists in each Gradle module's
  DI entry point.

---

## 2. Annotation Standards per Layer

Each architectural layer uses specific annotations to define lifecycle and scope.

| Component                      | Annotation       | Layer                            |
|:-------------------------------|:-----------------|:---------------------------------|
| **ViewModel**                  | `@KoinViewModel` | `:feature`                       |
| **UseCase / Mapper / Factory** | `@Factory`       | `:domain` / `:data` / `:feature` |
| **Repository / API Service**   | `@Single`        | `:data` / `:core`                |
| **Database / Network Client**  | `@Single`        | `:core`                          |

---

## 3. Constructor Injection (The Mandatory Way)

* **Rule:** Always use **Constructor Injection** for all classes (ViewModels, Repositories, etc.).
* **FORBIDDEN:** Do NOT use `get()` or `by inject()` inside classes. Let Koin inject them
  automatically via the constructor.
* **Constructor Order:** Remember to sort parameters **alphabetically** (as per `code-style.md`).

---

## 4. Handling Interfaces (Binds)

When a class implements an interface that should be injected:

* **Rule:** Use the `binds` parameter in `@Single` or `@Factory`.
* **Example:**

```kotlin
@Single(binds = [UserRepository::class])
internal class DefaultUserRepository(...) : UserRepository
```

---

## 5. Injected Parameters in UI (Composables)

Inside Jetpack Compose, use Koin-specific Compose functions:

* **ViewModels:** Use `koinViewModel<MyViewModel>()`.
* **Other Objects:** Use `koinInject<MyResources>()` only when necessary (prefer passing
  dependencies down from the Stateful Composable).

---

## 6. Self-Verification Checklist

Before finalizing DI changes, verify:

1. [ ] **No Manual Modules:** Did you avoid `module { ... }` blocks?
2. [ ] **Annotations:** Used `@KoinViewModel`, `@Factory`, or `@Single` appropriately?
3. [ ] **Binds:** Did you use `binds = [...]` for interface implementations?
4. [ ] **Constructor Injection:** Is the class receiving all dependencies via constructor?
5. [ ] **No get/inject:** Did you avoid using `get()` or `by inject()` inside the class?
6. [ ] **Alphabetical Order:** Are constructor parameters sorted alphabetically?
7. [ ] **Internal Modifier:** Are internal implementations marked as `internal`?
