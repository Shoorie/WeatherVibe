package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
fun rememberAppBackgroundBrush(): Brush {
  val gradientStart = colors.backgroundGradientStart
  val gradientEnd = colors.backgroundGradientEnd
  return remember(gradientStart, gradientEnd) {
    Brush.verticalGradient(listOf(gradientStart, gradientEnd))
  }
}
