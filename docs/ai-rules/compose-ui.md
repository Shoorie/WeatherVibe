# 🎨 Jetpack Compose & UI Guidelines (CRITICAL RULES)

> **Core Principle:** This project focuses on **Highly Modular, Resource-Injected UI** with a
> strict separation between Layout and Data.

## 📋 Table of Contents
1. [Design System & Theming — Token-Based Architecture](#1-design-system--theming--token-based-architecture-critical)
2. [Resource Management (The Wrapper Pattern)](#2-resource-management-the-wrapper-pattern)
3. [Aggressive Component Splitting & State Files](#3-aggressive-component-splitting--state-files)
4. [The Modifier Rule](#4-the-modifier-rule)
5. [Previews & External Mock Data (CRITICAL)](#5-previews--external-mock-data-critical)
6. [Stateless vs Stateful](#6-stateless-vs-stateful)
7. [Self-Verification Checklist](#7-self-verification-checklist)

---

## 1. Design System & Theming — Token-Based Architecture (CRITICAL)
* **Location:** All theme-related files MUST live in the `:core:designsystem` module, package
  `com.[company].[app].core.designsystem.theme`.
* **FORBIDDEN:** NEVER create theme files inside `:feature` modules.
* **Separation of Concerns:** The Design System MUST be split into separate files. NEVER dump
  everything into a single `Theme.kt` file.

### A. The File Structure (CRITICAL)
You must distribute the theme logic across the following files:

#### `Tokens.kt` (Raw values — `internal`)
Holds primitive values. Never accessed from feature modules directly.
* `internal object ColorTokens` (HEX values)
* `internal object FontSizeTokens`, `FontWeightTokens`, `LineHeightTokens`
* `internal object TypographyTokens` (Creates `TextStyle` from font tokens)

#### `Color.kt` (Semantic Layer)
Groups color tokens into meaningful UI concepts.
* `@Immutable data class AppColors(...)`
* Factory functions: `fun darkColors(): AppColors`

#### `Type.kt` & `Shape.kt` (Semantic Layer)
* `@Immutable data class AppTypography(...)` (Maps `TypographyTokens` to semantic names)
* `@Immutable data class AppShapes(...)`

#### `Dimens.kt` (Spacing & Sizing)
* e.g., `object AppDimens { val PaddingMedium = 16.dp }`

#### `Theme.kt` (Provider & Accessor)
Sets up `CompositionLocal` and wraps Material 3.

```kotlin
val LocalAppColors = staticCompositionLocalOf { darkColors() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
val LocalAppShapes = staticCompositionLocalOf { AppShapes() }

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  val colors = darkColors()
  CompositionLocalProvider(
    LocalAppColors provides colors,
    LocalAppTypography provides AppTypography(),
    LocalAppShapes provides AppShapes()
  ) {
    MaterialTheme(
      colorScheme = materialColorScheme(colors),
      typography = materialTypography(),
      content = content
    )
  }
}

object AppTheme {
  val colors: AppColors
    @Composable 
    @ReadOnlyComposable
    get() = LocalAppColors.current

  val typography: AppTypography
    @Composable 
    @ReadOnlyComposable 
    get() = LocalAppTypography.current

  val shapes: AppShapes
    @Composable 
    @ReadOnlyComposable 
    get() = LocalAppShapes.current
}
```

### B. Usage in Composables (CRITICAL - Static Imports ONLY)
* **FORBIDDEN:** Do NOT declare local variables for colors/typography (e.g., `val colors = 
  AppTheme.colors`).
* **FORBIDDEN:** Do NOT use the `AppTheme.` prefix in your UI layouts (e.g.,
  `AppTheme.colors.onBackground`).
* **FORBIDDEN:** Do NOT use `MaterialTheme.colorScheme.xxx`.
* **Rule:** You MUST statically import `colors`, `typography`, and `shapes` from the `AppTheme`
  object so they can be used directly as `colors.onBackground`.
* **Dimensions:** You MUST also use static imports from `AppDimens` to avoid prefixing.

```kotlin
// 1. Static imports for Theme components (CRITICAL)
import com.[company].[app].core.designsystem.theme.AppTheme.colors
import com.[company].[app].core.designsystem.theme.AppTheme.typography

// 2. Static import for dimensions
import com.[company].[app].core.designsystem.theme.AppDimens.PaddingMedium

@Composable
internal fun MyComponent(modifier: Modifier = Modifier) {
  Text(
    text = "Hello",
    // 3. Direct usage without any prefixes or local variables!
    style = typography.bodyMedium,
    color = colors.onBackground,
    modifier = modifier.padding(PaddingMedium)
  )
}
```

---

## 2. Resource Management (The Wrapper Pattern)
* **FORBIDDEN (CRITICAL):** NO user-facing strings or labels can be hardcoded as raw Strings 
  in code (including ViewModels, States, or Factories).
* **FORBIDDEN:** Never use `stringResource(R.string.xyz)` or `painterResource(R.drawable.xyz)`
  directly inside your Composable layout.
* **The Pattern:** For every screen, create an `internal class` resource wrapper 
  annotated with `@Factory` (e.g., `FeatureResources.kt`).
* **Non-Composable Usage (CRITICAL):** If resources are needed outside Composables (e.g., 
  in ViewModels), the wrapper MUST take `Context` in its constructor and provide regular 
  functions (not `@Composable`) that use `context.getString()`.
* **Rule:** All strings MUST be defined in `strings.xml` and accessed ONLY through the wrapper.
* **Clean Imports (CRITICAL):** Do NOT use prefixes like `Resources.Texts.title()`. Use Kotlin's
  static import feature to import functions directly for maximum readability.

### Wrapper Example:
```kotlin
@Factory
internal class FeatureResources(private val context: Context) {

  // 1. Regular function for non-composable usage (e.g., ViewModels/Factories)
  fun defaultError(): String = 
    context.getString(R.string.error)

    object Painters {
      
      @Composable
      fun icon(): Painter =
        painterResource(id = R.drawable.ic_feature)
    }


    object Texts {
    
      // 2. @Composable functions for UI usage
      @Composable
      fun title(): String = 
        stringResource(R.string.home_title)
  }
}
```

---

## 3. Aggressive Component Splitting & State Files
* **Strict Limit:** A single Composable function MUST NOT exceed 60 lines of code.
* **Feature-Specific State Files (CRITICAL):** Specialized screen states MUST be extracted to 
  their own separate files and MUST be prefixed with the Feature name to avoid collisions.
  * **Correct:** `HomeEmptyState.kt`, `HomeErrorState.kt`, `HomeLoadingState.kt`.
  * **Incorrect:** `EmptyState.kt`, `ErrorState.kt`.
* **Pragmatic Rule:** Even if a state is simple (e.g., a centered spinner), extract it as 
  `[Feature]LoadingState.kt` to maintain a clean top-level `when` structure.
* **Previews:** Every single file containing a Composable (including states and placeholders)
  MUST contain its own `@PreviewLightDark` function.

---

## 4. The Modifier Rule
* The `modifier: Modifier = Modifier` MUST ALWAYS be the FIRST optional parameter in every
  Composable function signature. Pass it down to the root layout of the component.

---

## 5. Previews & External Mock Data (CRITICAL)
* Every Stateless component MUST have a `@PreviewLightDark` function.
* **FORBIDDEN:** Do NOT include functional callbacks (lambdas/actions) in `PreviewParams`.
* **Rule:** `PreviewParams` should only contain visual data (Strings, Booleans, Domain Models).
  Pass empty lambdas `{}` directly in the `@Preview` function.
* **Location:** Each provider MUST live in its own file inside a `preview/` sub-package.

### A. Universal Preview Params Template (Data Only)
**File:** `feature/[name]/preview/[Component]PreviewParams.kt`
```kotlin
@Immutable
internal data class FeaturePreviewParams(
  val title: String,
  val subtitle: String? = null,
  val isLoading: Boolean = false
)
```

### B. Universal Preview Provider Template
**File:** `feature/[name]/preview/[Component]PreviewParameterProvider.kt`

**CRITICAL Rules:**
* Every preview value MUST be extracted into a named `private val` with a descriptive name.
* **FORBIDDEN:** Do NOT inline preview data directly inside `sequenceOf(...)`.
* The `override val values` MUST only reference the named properties.
* Each named val describes the preview scenario (e.g., `loading`, `error`).

```kotlin
internal class FeaturePreviewParameterProvider :
  PreviewParameterProvider<FeaturePreviewParams> {

  private val base : FeaturePreviewParams =
    FeaturePreviewParams(title = "Short Title")

  private val loading : FeaturePreviewParams =
    FeaturePreviewParams(
      title = "Loading...",
      isLoading = true
    )

  private val edgeCaseLongTitle : FeaturePreviewParams =
    FeaturePreviewParams(
      title = LoremIpsum(15).values.joinToString(" "),
      subtitle = "Detailed subtitle description"
    )

  override val values: Sequence<FeaturePreviewParams> =
    sequenceOf(base, loading, edgeCaseLongTitle)
}
```

### C. Usage with Explicit Lambdas
**File:** `feature/[name]/ui/[Component].kt`
```kotlin
@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(FeaturePreviewParameterProvider::class)
  params: FeaturePreviewParams
) {
  AppTheme {
    StatelessFeatureScreen(
      modifier = Modifier.fillMaxWidth(),
      params = params,
      onBackClick = {},
      onActionClick = {}
    )
  }
}
```

---

## 6. Stateless vs Stateful
* **Stateful Composable:** Responsible for collecting state from the ViewModel and passing it
  to the Stateless version.
* **Stateless Composable:** Takes raw data and lambda callbacks. This version MUST have the
  `@Preview` and use `Resources` injection.

---

## 7. Self-Verification Checklist
Before finalizing UI changes, verify:

1. [ ] **Theming:** No colors/typography accessed via `MaterialTheme` or `AppTheme.colors`?
2. [ ] **Static Imports:** Are `colors`, `typography`, and `PaddingXxx` statically imported?
3. [ ] **Resources (Strings):** Are ALL user-facing strings in `strings.xml`? No hardcoded Strings?
4. [ ] **Resources (Wrapper):** Are strings accessed ONLY through the Feature's Resource Wrapper?
5. [ ] **Non-Composable Resources:** Are resources used in ViewModels provided via `context.getString()`?
6. [ ] **Complexity:** No Composable function exceeds 60 lines?
7. [ ] **Splitting:** Are specialized screen states in separate files?
8. [ ] **Naming:** Are state files prefixed with the Feature name (e.g., `FeatureEmptyState`)?
9. [ ] **Previews:** Does EVERY file containing a Composable have its own `@PreviewLightDark`?
10. [ ] **Modifiers:** Is `modifier: Modifier = Modifier` the first optional parameter?
11. [ ] **PreviewProvider:** All preview values extracted to named `private val` properties?
12. [ ] **PreviewParams:** Only data included? (Lambdas passed as `{}` in Preview function?)
13. [ ] **State Separation:** Clear split between Stateful and Stateless?
14. [ ] **Stability:** Are all UI model classes and `PreviewParams` annotated with `@Immutable`?
