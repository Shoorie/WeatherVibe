package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class WeatherColors(
  val accent: Color,
  val accentDark: Color,
  val backgroundGradientEnd: Color,
  val backgroundGradientStart: Color,
  val error: Color,
  val glassBorder: Color,
  val glassSurface: Color,
  val glassSurfaceHeavy: Color,
  val onBackground: Color,
  val onError: Color,
  val onSurface: Color,
  val onSurfaceVariant: Color,
  val outline: Color,
  val surfaceVariant: Color,
  val textTertiary: Color
)

fun weatherDarkColors(): WeatherColors =
  WeatherColors(
    accent = ColorTokens.AccentSkyBlue,
    accentDark = ColorTokens.AccentSkyBlueDark,
    backgroundGradientEnd = ColorTokens.BackgroundGradientEnd,
    backgroundGradientStart = ColorTokens.BackgroundGradientStart,
    error = ColorTokens.ConditionThunder,
    glassBorder = ColorTokens.GlassBorder,
    glassSurface = ColorTokens.GlassSurface,
    glassSurfaceHeavy = ColorTokens.GlassSurfaceHeavy,
    onBackground = ColorTokens.TextPrimary,
    onError = ColorTokens.TextPrimary,
    onSurface = ColorTokens.TextPrimary,
    onSurfaceVariant = ColorTokens.TextSecondary,
    outline = ColorTokens.GlassBorder,
    surfaceVariant = ColorTokens.GlassSurface,
    textTertiary = ColorTokens.TextTertiary
  )
