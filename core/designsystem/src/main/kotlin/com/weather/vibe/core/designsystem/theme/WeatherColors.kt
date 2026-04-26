package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class WeatherColors(
  val accent: Color,
  val accentDark: Color,
  val appBackgroundStart: Color,
  val backgroundGradientEnd: Color,
  val backgroundGradientStart: Color,
  val cardContainer: Color,
  val error: Color,
  val glassBorder: Color,
  val glassSurface: Color,
  val glassSurfaceHeavy: Color,
  val onAccent: Color,
  val onBackground: Color,
  val onError: Color,
  val onSurface: Color,
  val onSurfaceVariant: Color,
  val outline: Color,
  val outlineVariant: Color,
  val primaryContainer: Color,
  val onPrimaryContainer: Color,
  val sheetSurface: Color,
  val surfaceVariant: Color,
  val colorCool: Color,
  val colorWarm: Color,
  val textTertiary: Color,
  val pillPalette: ImmutableList<Color>
)

fun weatherDarkColors(): WeatherColors =
  WeatherColors(
    accent = ColorTokens.AccentSkyBlue,
    accentDark = ColorTokens.AccentSkyBlueDark,
    appBackgroundStart = ColorTokens.BackgroundGradientStart,
    backgroundGradientEnd = ColorTokens.BackgroundGradientEnd,
    backgroundGradientStart = ColorTokens.BackgroundGradientStart,
    cardContainer = ColorTokens.DarkCardContainer,
    colorCool = ColorTokens.PaletteBlue,
    colorWarm = ColorTokens.PaletteAmber,
    error = ColorTokens.DarkError,
    glassBorder = ColorTokens.GlassBorder,
    glassSurface = ColorTokens.GlassSurface,
    glassSurfaceHeavy = ColorTokens.GlassSurfaceHeavy,
    onAccent = ColorTokens.OnAccent,
    onBackground = ColorTokens.TextPrimary,
    onError = ColorTokens.TextPrimary,
    onSurface = ColorTokens.TextPrimary,
    onSurfaceVariant = ColorTokens.TextSecondary,
    outline = ColorTokens.GlassBorder,
    outlineVariant = ColorTokens.GlassBorderSubtle,
    primaryContainer = ColorTokens.AccentSkyBlueDark,
    onPrimaryContainer = ColorTokens.TextPrimary,
    sheetSurface = ColorTokens.SheetSurface,
    surfaceVariant = ColorTokens.GlassSurface,
    textTertiary = ColorTokens.TextTertiary,
    pillPalette = persistentListOf(
      ColorTokens.DarkPillCoral,
      ColorTokens.DarkPillBlue,
      ColorTokens.DarkPillAmber,
      ColorTokens.DarkPillViolet,
      ColorTokens.DarkPillTeal,
      ColorTokens.DarkPillRose
    )
  )

fun weatherLightColors(): WeatherColors =
  WeatherColors(
    accent = ColorTokens.LightPrimary,
    accentDark = ColorTokens.LightPrimary,
    appBackgroundStart = ColorTokens.LightPrimaryContainer,
    backgroundGradientEnd = ColorTokens.LightBackground,
    backgroundGradientStart = ColorTokens.LightBackground,
    cardContainer = ColorTokens.LightPrimaryContainer,
    error = ColorTokens.LightError,
    glassBorder = ColorTokens.LightOutlineVariant,
    glassSurface = ColorTokens.LightSurface,
    glassSurfaceHeavy = ColorTokens.LightSurfaceContainer,
    onAccent = ColorTokens.OnAccent,
    onBackground = ColorTokens.LightOnSurface,
    onError = ColorTokens.LightSurface,
    onSurface = ColorTokens.LightOnSurface,
    onSurfaceVariant = ColorTokens.LightOnSurfaceVariant,
    outline = ColorTokens.LightOutline,
    outlineVariant = ColorTokens.LightOutlineVariant,
    primaryContainer = ColorTokens.LightPrimaryContainer,
    onPrimaryContainer = ColorTokens.LightOnPrimaryContainer,
    colorCool = ColorTokens.LightCool,
    colorWarm = ColorTokens.LightWarm,
    sheetSurface = ColorTokens.LightSurface,
    surfaceVariant = ColorTokens.LightSurfaceContainer,
    textTertiary = ColorTokens.LightTextTertiary,
    pillPalette = persistentListOf(
      ColorTokens.LightPillCoral,
      ColorTokens.LightPillBlue,
      ColorTokens.LightPillAmber,
      ColorTokens.LightPillViolet,
      ColorTokens.LightPillTeal,
      ColorTokens.LightPillRose
    )
  )
