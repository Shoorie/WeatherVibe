# Code Style & File Structure

# Code Style & File Structure

1. **One Concept = One File:**
    * Every `data class`, `enum class`, `sealed class/interface`, or `interface` MUST be in its own separate `.kt` file. Do not group them into generic "Models.kt" or "Types.kt".
2. **Kotlin Formatting:**
    * Maintain standard Kotlin formatting (ktlint). Leave blank lines between functions.
    * Class members order: Properties, `init` blocks, secondary constructors, public functions, private functions, `companion object`.
3. **Naming Conventions:**
    * Files containing a single Composable function should be named after that function (e.g., `HourlyForecastChart.kt`).
