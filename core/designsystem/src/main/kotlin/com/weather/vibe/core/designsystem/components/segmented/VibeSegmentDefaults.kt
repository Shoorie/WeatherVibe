package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography

object VibeSegmentDefaults {

  val MinHeight = 44.dp
  val Padding = 4.dp

  @Composable
  @ReadOnlyComposable
  fun segmentTextColor(selected: Boolean): Color =
    if (selected) colors.onAccent else colors.onSurfaceVariant

  @Composable
  fun segmentTextStyle(selected: Boolean): TextStyle {
    val base = typography.labelMedium
    return remember(base, selected) {
      base.copy(fontWeight = if (selected) SemiBold else Medium)
    }
  }
}
