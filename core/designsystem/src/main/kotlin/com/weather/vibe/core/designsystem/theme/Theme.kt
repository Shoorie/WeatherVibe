package com.weather.vibe.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWeatherColors = staticCompositionLocalOf { weatherDarkColors() }
val LocalWeatherTypography = staticCompositionLocalOf { WeatherTypography() }
val LocalWeatherShapes = staticCompositionLocalOf { WeatherShapes() }

@Composable
fun WeatherVibeTheme(content: @Composable () -> Unit) {
  val colors = weatherDarkColors()
  CompositionLocalProvider(
    LocalWeatherColors provides colors,
    LocalWeatherTypography provides WeatherTypography(),
    LocalWeatherShapes provides WeatherShapes()
  ) {
    MaterialTheme(
      colorScheme = materialColorScheme(colors),
      typography = materialTypography(),
      content = content
    )
  }
}

object WeatherVibeTheme {

  val colors: WeatherColors
    @Composable @ReadOnlyComposable
    get() = LocalWeatherColors.current

  val typography: WeatherTypography
    @Composable @ReadOnlyComposable
    get() = LocalWeatherTypography.current

  val shapes: WeatherShapes
    @Composable @ReadOnlyComposable
    get() = LocalWeatherShapes.current
}

private fun materialColorScheme(colors: WeatherColors): ColorScheme =
  darkColorScheme(
    primary = colors.accent,
    onPrimary = colors.backgroundGradientEnd,
    primaryContainer = colors.glassSurfaceHeavy,
    onPrimaryContainer = colors.accent,
    secondary = colors.onSurfaceVariant,
    onSecondary = colors.backgroundGradientEnd,
    background = colors.backgroundGradientEnd,
    onBackground = colors.onBackground,
    surface = colors.backgroundGradientStart,
    onSurface = colors.onSurface,
    surfaceVariant = colors.surfaceVariant,
    onSurfaceVariant = colors.onSurfaceVariant,
    outline = colors.outline,
    error = colors.error,
    onError = colors.onError
  )

private fun materialTypography(): Typography =
  Typography(
    displayLarge = TypographyTokens.DisplayLarge,
    displayMedium = TypographyTokens.DisplayMedium,
    displaySmall = TypographyTokens.DisplaySmall,
    headlineLarge = TypographyTokens.HeadlineLarge,
    headlineMedium = TypographyTokens.HeadlineMedium,
    titleLarge = TypographyTokens.TitleLarge,
    titleMedium = TypographyTokens.TitleMedium,
    titleSmall = TypographyTokens.TitleSmall,
    bodyLarge = TypographyTokens.BodyLarge,
    bodyMedium = TypographyTokens.BodyMedium,
    bodySmall = TypographyTokens.BodySmall,
    labelMedium = TypographyTokens.LabelMedium,
    labelSmall = TypographyTokens.LabelSmall
  )
