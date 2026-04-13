package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeDefaults.BriefingMutedAlpha

internal object HomeTextStyles {

  @Composable
  fun semiBold(style: TextStyle): TextStyle =
    remember(style) { style.copy(fontWeight = FontWeight.SemiBold) }

  @Composable
  fun dayColor(isToday: Boolean): Color =
    if (isToday) colors.accent else colors.onBackground

  @Composable
  fun dayStyle(isToday: Boolean): TextStyle {
    val base = typography.bodyMedium
    val bold = semiBold(base)
    return if (isToday) bold else base
  }

  @Composable
  fun mutedOnPrimaryContainer(): Color =
    colors.onPrimaryContainer
      .copy(alpha = BriefingMutedAlpha)
}
