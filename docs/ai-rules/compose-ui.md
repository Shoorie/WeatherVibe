# Jetpack Compose & Figma Guidelines

1. **Figma Translation & Theming (CRITICAL):**
    * When provided with a Figma link or UI spec, DO NOT hardcode colors or font sizes in the Composable.
    * **Colors:** Extract all HEX values to `ui/theme/Color.kt` and define a proper `ColorScheme`.
    * **Typography:** Group font sizes and weights logically into `ui/theme/Type.kt` using Material 3 naming (e.g., `DisplayLarge` for huge temps, `BodySmall` for labels).
    * **Dimensions:** Create an `AppDimens` object or use `CompositionLocal` for paddings, spacings, and corner radiuses. No magic numbers (like `padding(16.dp)`) scattered in the code.
2. **Aggressive Component Splitting:**
    * Break down EVERY UI screen into small, private, reusable functions. No monolithic Composables.
3. **The Modifier Rule:**
    * The `modifier: Modifier = Modifier` MUST ALWAYS be the FIRST optional parameter in every Composable function signature.
4. **Previews:**
    * Every Stateless screen and component must have a `@Preview(showBackground = true)` function with mock data (use preview parameters with various data and states).
