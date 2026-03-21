# Jetpack Compose & UI Guidelines (CRITICAL RULES)

This project focuses on **Highly Modular, Resource-Injected UI** with a strict separation between Layout and Data.

## 1. Design System & Theming — Token-Based Architecture (CRITICAL)
* **Location:** All theme-related files MUST live in the `:core:designsystem` module, package `com.[company].[app].core.designsystem.theme`.
* **FORBIDDEN:** NEVER create theme files inside `:feature` modules.
* **Separation of Concerns:** The Design System MUST be split into separate files. NEVER dump everything into a single `Theme.kt` file.

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
* `object AppDimens { val PaddingMedium = 16.dp }`

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
* **FORBIDDEN:** Do NOT declare local variables for colors/typography (e.g., `val colors = AppTheme.colors`).
* **FORBIDDEN:** Do NOT use the `AppTheme.` prefix in your UI layouts (e.g., `AppTheme.colors.onBackground`).
* **FORBIDDEN:** Do NOT use `MaterialTheme.colorScheme.xxx`.
* **Rule:** You MUST statically import `colors`, `typography`, and `shapes` from the `AppTheme` object so they can be used directly as `colors.onBackground`.
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

## 2. Resource Management (The Wrapper Pattern)
* **FORBIDDEN:** Never use `stringResource(R.string.xyz)` or `painterResource(R.drawable.xyz)` directly inside your Composable layout.
* **The Pattern:** For every screen, create a `internal object` resource wrapper in the feature's package.
* **Clean Imports (CRITICAL):** Do NOT use prefixes like `Resources.Texts.title()`. Use Kotlin's static import feature to import functions directly for maximum readability.

```kotlin
internal object FeatureResources {

    object Painters {

        @Composable
        fun icon(): Painter =
          painterResource(id = R.drawable.ic_feature)
    }

    object Texts {

        @Composable
        fun title(): String =
          stringResource(R.string.txt_feature_title)
    }
}
```

## 3. Aggressive Component Splitting
* **Strict Limit:** A single Composable function MUST NOT exceed 60 lines of code.
* Break down EVERY UI screen into small, private, reusable functions. Extract Headers, Lists, and Cards immediately.

## 4. The Modifier Rule
* The `modifier: Modifier = Modifier` MUST ALWAYS be the FIRST optional parameter in every Composable
function signature. Pass it down to the root layout of the component.

## 5. Previews & External Mock Data (CRITICAL)
* Every Stateless component MUST have a `@PreviewLightDark` function.
* **FORBIDDEN:** Do NOT include functional callbacks (lambdas/actions) in `PreviewParams`.
* **Rule:** `PreviewParams` should only contain visual data (Strings, Booleans, Domain Models). Pass empty lambdas `{}` directly in the `@Preview` function.
* **Location:** Each provider MUST live in its own file inside a `preview/` sub-package (e.g., `feature/home/preview/HomePreviewParameterProvider.kt`).

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

### B. Universal Preview Provider Template (CRITICAL — follow this exact style)
**File:** `feature/[name]/preview/[Component]PreviewParameterProvider.kt`

**CRITICAL Rules:**
* Every preview value MUST be extracted into a named `private val` with a descriptive name.
* **FORBIDDEN:** Do NOT inline preview data directly inside `sequenceOf(...)`.
* The `override val values` MUST only reference the named properties - it serves as a clean table of contents.
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
  WeatherVibeTheme {
    StatelessFeatureScreen(
      modifier = Modifier.fillMaxWidth(),
      params = params,
      onBackClick = {},
      onActionClick = {}
    )
  }
}
```

## 6. Stateless vs Stateful
* **Stateful Composable:** Responsible for collecting state from the ViewModel and passing it to the Stateless version.
* **Stateless Composable:** Takes raw data and lambda callbacks. This version MUST have the `@Preview` and use `Resources` injection.
