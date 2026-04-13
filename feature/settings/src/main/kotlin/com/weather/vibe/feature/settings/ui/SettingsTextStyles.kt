package com.weather.vibe.feature.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

internal object SettingsTextStyles {

  @Composable
  fun briefToneRowBackground(isSelected: Boolean): Color =
    if (isSelected) colors.primaryContainer else Color.Transparent

  @Composable
  fun briefToneLabelColor(isSelected: Boolean): Color =
    if (isSelected) colors.onPrimaryContainer else colors.onBackground

  @Composable
  fun briefToneDescriptionColor(isSelected: Boolean): Color =
    if (isSelected) colors.onPrimaryContainer else colors.onSurfaceVariant

  @Composable
  fun briefToneLabelStyle(isSelected: Boolean): TextStyle {
    val base = typography.bodyMedium
    return remember(base, isSelected) {
      if (isSelected) base.copy(fontWeight = FontWeight.SemiBold) else base
    }
  }

  @Composable
  fun segmentBackground(isSelected: Boolean): Color =
    if (isSelected) colors.accent else Color.Transparent

  @Composable
  fun segmentLabelColor(isSelected: Boolean): Color =
    if (isSelected) colors.onAccent else colors.onSurfaceVariant

  @Composable
  fun segmentLabelStyle(isSelected: Boolean): TextStyle {
    val base = typography.labelMedium
    return remember(base, isSelected) {
      base.copy(fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium)
    }
  }
}
