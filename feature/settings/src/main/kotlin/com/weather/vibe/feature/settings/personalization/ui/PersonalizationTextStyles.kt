package com.weather.vibe.feature.settings.personalization.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.settings.personalization.ui.PersonalizationDefaults.SelectedTintAlpha

internal object PersonalizationTextStyles {

  @Composable
  fun briefToneRowBackground(isSelected: Boolean): Color =
    if (isSelected) colors.accent.copy(alpha = SelectedTintAlpha) else Color.Transparent

  @Composable
  fun briefToneLabelColor(isSelected: Boolean): Color =
    if (isSelected) colors.onBackground else colors.onBackground

  @Composable
  fun briefToneDescriptionColor(isSelected: Boolean): Color =
    colors.onSurfaceVariant

  @Composable
  fun briefToneLabelStyle(isSelected: Boolean): TextStyle {
    val base = typography.bodyMedium
    return remember(base, isSelected) {
      if (isSelected) base.copy(fontWeight = SemiBold) else base
    }
  }
}
