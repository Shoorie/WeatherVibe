package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

object VibeSegmentedDefaults {

  val MinHeight = 44.dp
  val Padding = 4.dp

  @Composable
  @ReadOnlyComposable
  fun segmentBackgroundColor(selected: Boolean): Color =
    if (selected) colors.accent else Transparent

  @Composable
  @ReadOnlyComposable
  fun segmentTextColor(selected: Boolean): Color =
    if (selected) colors.onAccent else colors.onSurfaceVariant

  @Composable
  @ReadOnlyComposable
  fun segmentTextStyle(selected: Boolean): TextStyle =
    typography.labelMedium
      .copy(fontWeight = if (selected) SemiBold else Medium)
}
