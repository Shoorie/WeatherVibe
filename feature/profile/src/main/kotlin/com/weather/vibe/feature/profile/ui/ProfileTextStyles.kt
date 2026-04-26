package com.weather.vibe.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

internal object ProfileTextStyles {

  @Composable
  fun segmentChip(isSelected: Boolean): TextStyle =
    typography.labelMedium.copy(fontWeight = if (isSelected) SemiBold else Medium)
}
