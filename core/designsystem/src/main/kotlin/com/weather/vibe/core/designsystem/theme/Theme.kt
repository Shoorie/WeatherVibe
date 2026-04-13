package com.weather.vibe.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWeatherColors = staticCompositionLocalOf { weatherLightColors() }
val LocalWeatherTypography = staticCompositionLocalOf { WeatherTypography() }
val LocalWeatherShapes = staticCompositionLocalOf { WeatherShapes() }

@Composable
fun WeatherVibeTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {

  val colors = remember(darkTheme) { if (darkTheme) weatherDarkColors() else weatherLightColors() }
  val typography = remember { WeatherTypography() }
  val shapes = remember { WeatherShapes() }
  val colorScheme = remember(colors, darkTheme) { materialColorScheme(colors, darkTheme) }
  val materialTypography = remember { materialTypography() }

  CompositionLocalProvider(
    LocalWeatherColors provides colors,
    LocalWeatherTypography provides typography,
    LocalWeatherShapes provides shapes
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = materialTypography,
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

private fun materialColorScheme(colors: WeatherColors, darkTheme: Boolean): ColorScheme =
  if (darkTheme) {
    darkColorScheme(
      primary = colors.accent,
      onPrimary = colors.onAccent,
      primaryContainer = colors.primaryContainer,
      onPrimaryContainer = colors.onPrimaryContainer,
      secondary = colors.onSurfaceVariant,
      onSecondary = colors.onAccent,
      background = colors.backgroundGradientEnd,
      onBackground = colors.onBackground,
      surface = colors.glassSurface,
      onSurface = colors.onSurface,
      surfaceVariant = colors.surfaceVariant,
      onSurfaceVariant = colors.onSurfaceVariant,
      outline = colors.outline,
      error = colors.error,
      onError = colors.onError
    )
  } else {
    lightColorScheme(
      primary = colors.accent,
      onPrimary = colors.onAccent,
      primaryContainer = colors.primaryContainer,
      onPrimaryContainer = colors.onPrimaryContainer,
      secondary = colors.onSurfaceVariant,
      onSecondary = colors.onAccent,
      background = colors.backgroundGradientEnd,
      onBackground = colors.onBackground,
      surface = colors.glassSurface,
      onSurface = colors.onSurface,
      surfaceVariant = colors.surfaceVariant,
      onSurfaceVariant = colors.onSurfaceVariant,
      outline = colors.outline,
      error = colors.error,
      onError = colors.onError
    )
  }

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
