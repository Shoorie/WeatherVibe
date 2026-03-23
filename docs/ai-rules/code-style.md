# 🎨 Code Style & File Structure (CRITICAL RULES)

> **Core Principle:** This project enforces a strict, clean, and concise Kotlin style. Every line
> of code must look intentional and follow the established formatting.

## 📋 Table of Contents

1. [Formatting & Standards (CRITICAL)](#1-formatting--standards-critical)
2. [Naming Conventions (Concise & Meaningful)](#2-naming-conventions-concise--meaningful)
3. [File Structure & Separation](#3-file-structure--separation)
4. [Constructor Parameter Order (CRITICAL)](#4-constructor-parameter-order-critical)
5. [Class Member Order](#5-class-member-order)
6. [Function & Constructor Calls (CRITICAL)](#6-function--constructor-calls-critical)
7. [Clean Imports & Hygiene (NO LEFTOVERS)](#7-clean-imports--hygiene-no-leftovers)
8. [No Hardcoded Values](#8-no-hardcoded-values)
9. [Nullability & Safety](#9-nullability--safety)
10. [Self-Verification Checklist](#10-self-verification-checklist)

---

## 1. Formatting & Standards (CRITICAL)

* **EditorConfig:** This project uses a `.editorconfig` file as the single source of truth for
  indentation, line length, and basic formatting. All generated code MUST comply with it.
* **Braces:** Use Egyptian/K&R style (opening brace on the same line, closing brace on a new line).
* **Empty Lines:** Use single blank lines to separate functions and logic blocks. Do not use
  multiple blank lines.

---

## 2. Naming Conventions (Concise & Meaningful)

* **Rule:** Names should be short but descriptive. Avoid redundancy and "manager-speak".
* **Classes:** Use `DefaultUserRepository` instead of `DefaultUserRepositoryImplementation`. Avoid
  overly long names like `UserAccountScreenDetailsContainer`. Use `UserDetails` instead.
* **Functions:** Name them as actions (e.g., `fetchData()`, `onItemClick()`).
* **Composables:** Files containing a single Composable function MUST be named exactly after that
  function (e.g., `ProfileCard.kt` for `fun ProfileCard`).

---

## 3. File Structure & Separation

* **One Concept = One File:** Every `data class`, `enum class`, `sealed interface`, or `interface`
  MUST be in its own separate `.kt` file.
* **FORBIDDEN:** Do NOT group multiple models into a single `Models.kt` or `Common.kt` file.
* **Package Declaration:** Always match the physical directory structure.

---

## 4. Constructor Parameter Order (CRITICAL)

To maintain clean diffs and maximize scannability:

> **Rule:** Constructor parameters MUST be sorted **alphabetically** by parameter name.
> This applies to ALL classes: ViewModels, Use Cases, Repositories, Factories, API Services.

```kotlin
@KoinViewModel
internal class HomeViewModel(
  private val factory: HomeStateFactory,
  private val fetchUserProfile: FetchUserProfile,
  private val searchItems: SearchItems
) : ViewModel()
```

---

## 5. Class Member Order

Organize class members in the following strict order:

1. Properties (Constants first, then `private`, then `public`).
2. `init` blocks.
3. Secondary constructors.
4. Overridden public functions.
5. Other public functions.
6. Private helper functions.
7. `companion object`.

---

## 6. Function & Constructor Calls (CRITICAL)

To maximize readability and prevent errors due to parameter swapping:

### A. Named Arguments Requirement

**Named Arguments MUST be used for:**

* **ALL** function or constructor calls with **more than 1 argument**.
* **ALL** Literal arguments (**Boolean, Int, Double, String, etc.**) and **null** (even if it is the
  only argument).
* **Exception:** Named arguments can be omitted for a single parameter if the function name clearly
  describes it (e.g., `delay(1000)`, `setAge(25)`).
* **ALL** arguments where the meaning is not immediately obvious from the value itself.

### B. Vertical Formatting (Multi-line)

**Vertical Formatting (one parameter per line) MUST be used when:**

* The call exceeds the line length limit defined in EditorConfig.
* The call has **more than 2 arguments**.

```kotlin
// 1. Multi-argument call (Named + Vertical)
val user = User(
  age = 25,
  city = "Toronto",
  name = "Jan"
)

// 2. Two-argument call (Named + Single line if short)
val pair = Point(x = 10, y = 20)

// 3. Single Literal/Null argument (Named required)
toggle(isEnabled = true)
calculate(radius = 10.5)
update(label = null)

// 4. Clear Context Exception (No name needed)
delay(1000)
```

---

## 7. Clean Imports & Hygiene (NO LEFTOVERS)

* **FORBIDDEN:** Avoid wildcard imports (`import com.example.*`).
* **Static Imports:** Use static imports for members of Sealed Classes, Enums, and Resource Wrappers
  to keep the logic clean (e.g., `is Loaded -> ...` instead of `is State.Loaded -> ...`).
* **Unused Imports:** Always remove unused imports before finalizing a file.
* **Dead Code:** Delete unused variables, functions, and commented-out code blocks.
* **Resources:** If you rename or delete a feature, ensure corresponding `strings.xml` or drawables
  are also removed if no longer used.

---

## 8. No Hardcoded Values

* **FORBIDDEN:** Never use raw hardcoded strings, format patterns, symbols, or magic numbers
  inline in code. Extract them to a `private companion object` with named constants.

---

## 9. Nullability & Safety

* Prefer `val` over `var` whenever possible.
* Use Kotlin's null-safety features (`?.`, `?:`, `let`) instead of force-unwrapping (`!!`).
* For Composables, use nullable types only when the data is truly optional.

---

## 10. Self-Verification Checklist

Before finalizing code changes, verify:

1. [ ] **EditorConfig:** Does your code align with the `.editorconfig` settings (indentation, line
   length)?
2. [ ] **One Concept Per File:** Are all classes/interfaces in their own files?
3. [ ] **Constructor Sorting:** Are constructor parameters sorted alphabetically?
4. [ ] **Member Order:** Are class members in the correct strict order?
5. [ ] **Named Arguments:** Used for all literals (Boolean, Int, etc.), nulls, and calls > 1
   argument?
6. [ ] **Vertical Formatting:** Used for all calls > 2 arguments or when exceeding line limit?
7. [ ] **Imports:** No wildcards? All unused imports removed?
8. [ ] **Hardcoding:** No raw strings/numbers? (Used `companion object` constants?)
9. [ ] **Naming:** Concise names used? (No `Impl`, matching file names for Composables?)
10. [ ] **Hygiene:** All dead code, commented-out blocks, and unused resources removed?
11. [ ] **Null Safety:** No `!!` used?
