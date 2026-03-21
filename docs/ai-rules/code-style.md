# Code Style & File Structure (CRITICAL RULES)

This project enforces a strict, clean, and concise Kotlin style. Every line of code must look intentional
and follow the established formatting.

## 1. Formatting & Indentation (CRITICAL)
* **Indentation:** Use exactly **2 spaces** for indentation. DO NOT use 4 spaces or tabs.
* **Line Length:** Maximum line length is **100 characters**. Wrap lines logically if they exceed this limit.
* **Braces:** Use Egyptian/K&R style (opening brace on the same line, closing brace on a new line).
* **Empty Lines:** Use single blank lines to separate functions and logic blocks. Do not use multiple blank lines.

```kotlin
class ExampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        MainScreen()
      }
    }
  }
}
```

## 2. Naming Conventions (Concise & Meaningful)
* **Rule:** Names should be short but descriptive. Avoid redundancy and "manager-speak".
* **Classes:** Use `DefaultWeatherRepository` instead of `DefaultWeatherRepositoryImplementation`. Avoid overly long names like `WeatherInformationScreenDetailsContainer`. Use `WeatherDetails` instead.
* **Functions:** Name them as actions (e.g., `fetchData()`, `onItemClick()`).
* **Composables:** Files containing a single Composable function MUST be named exactly after that function (e.g., `WeatherCard.kt` for `fun WeatherCard`).

## 3. File Structure & Separation
* **One Concept = One File:** Every `data class`, `enum class`, `sealed interface`, or `interface` MUST be in its own separate `.kt` file.
* **FORBIDDEN:** Do NOT group multiple models into a single `Models.kt` or `Common.kt` file.
* **Package Declaration:** Always match the physical directory structure.

## 4. Constructor Parameter Order (CRITICAL)
* **Rule:** Constructor parameters MUST be sorted **alphabetically** by parameter name.
* This applies to ALL classes: ViewModels, Use Cases, Repositories, Factories, API Services.
* **Example:**
```kotlin
@KoinViewModel
internal class HomeViewModel(
  private val getWeather: GetWeather,
  private val searchLocation: SearchLocation,
  private val stateFactory: HomeStateFactory
) : ViewModel()
```

## 5. Class Member Order
Organize class members in the following strict order:
1. Properties (Constants first, then `private`, then `public`).
2. `init` blocks.
3. Secondary constructors.
4. Overridden public functions.
5. Other public functions.
6. Private helper functions.
7. `companion object`.

## 6. Clean Imports
* **FORBIDDEN:** Avoid wildcard imports (`import com.example.*`).
* **Static Imports:** Use static imports for members of Sealed Classes, Enums, and Resource Wrappers to keep the logic clean (e.g., `is Loaded -> ...` instead of `is State.Loaded -> ...`).
* **Unused Imports:** Always remove unused imports before finalizing a file.

## 7. Nullability & Safety
* Prefer `val` over `var` whenever possible.
* Use Kotlin's null-safety features (`?.`, `?:`, `let`) instead of force-unwrapping (`!!`).
* For Composables, use nullable types only when the data is truly optional.
