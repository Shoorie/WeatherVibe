package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
@ReadOnlyComposable
internal fun labelPillContainerColor(seed: Long): Color {
  val palette = colors.pillPalette
  val bucket = (seed % palette.size + palette.size) % palette.size
  return palette[bucket.toInt()]
}
