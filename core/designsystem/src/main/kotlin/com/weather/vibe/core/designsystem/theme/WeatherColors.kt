package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val SELECTED_ROW_OVERLAY_ALPHA = 0.20f

@Immutable
data class WeatherColors(
  val accent: Color,
  val accentDark: Color,
  val appBackgroundStart: Color,
  val backgroundGradientEnd: Color,
  val backgroundGradientStart: Color,
  val cardContainer: Color,
  val error: Color,
  val glassSurface: Color,
  val glassSurfaceHeavy: Color,
  val heroSurface: Color,
  val onAccent: Color,
  val onBackground: Color,
  val onError: Color,
  val onSurface: Color,
  val onSurfaceVariant: Color,
  val outline: Color,
  val outlineVariant: Color,
  val popupSurface: Color,
  val primaryContainer: Color,
  val onPrimaryContainer: Color,
  val rowSurface: Color,
  val screenSurface: Color,
  val selectedRowSurface: Color,
  val sheetSurface: Color,
  val success: Color,
  val surfaceVariant: Color,
  val colorCool: Color,
  val colorWarm: Color,
  val textTertiary: Color,
  val pillPalette: ImmutableList<Color>
)

fun weatherDarkColors(): WeatherColors =
  WeatherColors(
    accent = ColorTokens.AccentLavender,
    accentDark = ColorTokens.AccentIndigo,
    appBackgroundStart = ColorTokens.BackgroundGradientStart,
    backgroundGradientEnd = ColorTokens.BackgroundGradientEnd,
    backgroundGradientStart = ColorTokens.BackgroundGradientStart,
    cardContainer = ColorTokens.DarkCardContainer,
    colorCool = ColorTokens.PaletteBlue,
    colorWarm = ColorTokens.PaletteAmber,
    error = ColorTokens.DarkError,
    glassSurface = ColorTokens.GlassSurface,
    glassSurfaceHeavy = ColorTokens.GlassSurfaceHeavy,
    heroSurface = ColorTokens.DarkCardContainer,
    onAccent = ColorTokens.OnAccent,
    onBackground = ColorTokens.TextPrimary,
    onError = ColorTokens.TextPrimary,
    onSurface = ColorTokens.TextPrimary,
    onSurfaceVariant = ColorTokens.TextSecondary,
    outline = ColorTokens.GlassBorder,
    outlineVariant = ColorTokens.GlassBorderSubtle,
    popupSurface = ColorTokens.DarkCardContainer,
    primaryContainer = ColorTokens.DarkCardContainer,
    onPrimaryContainer = ColorTokens.TextPrimary,
    rowSurface = ColorTokens.DarkCardContainer,
    screenSurface = ColorTokens.BackgroundGradientEnd,
    selectedRowSurface = ColorTokens.AccentLavender.copy(alpha = SELECTED_ROW_OVERLAY_ALPHA),
    sheetSurface = ColorTokens.SheetSurface,
    success = ColorTokens.DarkSuccess,
    surfaceVariant = ColorTokens.DarkCardContainer,
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
    glassSurface = ColorTokens.LightSurface,
    glassSurfaceHeavy = ColorTokens.LightSurfaceContainer,
    heroSurface = ColorTokens.LightPrimary,
    onAccent = ColorTokens.OnAccent,
    onBackground = ColorTokens.LightOnSurface,
    onError = ColorTokens.LightSurface,
    onSurface = ColorTokens.LightOnSurface,
    onSurfaceVariant = ColorTokens.LightOnSurfaceVariant,
    outline = ColorTokens.LightOutline,
    outlineVariant = ColorTokens.LightOutlineVariant,
    popupSurface = ColorTokens.LightSurface,
    primaryContainer = ColorTokens.LightPrimaryContainer,
    onPrimaryContainer = ColorTokens.LightOnPrimaryContainer,
    colorCool = ColorTokens.LightCool,
    colorWarm = ColorTokens.LightWarm,
    rowSurface = ColorTokens.LightSurface,
    screenSurface = ColorTokens.LightSurface,
    selectedRowSurface = ColorTokens.LightPrimaryContainer,
    sheetSurface = ColorTokens.LightSurface,
    success = ColorTokens.LightSuccess,
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
