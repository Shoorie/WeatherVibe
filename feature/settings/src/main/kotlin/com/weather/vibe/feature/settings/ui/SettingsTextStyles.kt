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
}
