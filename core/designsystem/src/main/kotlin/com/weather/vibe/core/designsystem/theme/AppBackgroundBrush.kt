package com.weather.vibe.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
fun rememberAppBackgroundBrush(): Brush {
  val start = colors.appBackgroundStart
  val end = colors.backgroundGradientEnd
  return remember(start, end) {
    Brush.verticalGradient(listOf(start, end))
  }
}
