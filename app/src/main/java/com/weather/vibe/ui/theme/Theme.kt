package com.weather.vibe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val WeatherDarkColorScheme = darkColorScheme(
    primary = AccentSkyBlue,
    onPrimary = BackgroundGradientEnd,
    primaryContainer = GlassSurfaceHeavy,
    onPrimaryContainer = AccentSkyBlue,
    secondary = TextSecondary,
    onSecondary = BackgroundGradientEnd,
    background = BackgroundGradientEnd,
    onBackground = TextPrimary,
    surface = BackgroundGradientStart,
    onSurface = TextPrimary,
    surfaceVariant = GlassSurface,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorder,
    error = ConditionThunder,
    onError = TextPrimary
)

@Composable
fun WeatherVibeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WeatherDarkColorScheme,
        typography = AppTypography,
        content = content
    )
}
