package com.weather.vibe.feature.locations.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
@ReadOnlyComposable
internal fun labelPillContainerColor(index: Int): Color {
  val palette = colors.pillPalette
  return palette[(index % palette.size + palette.size) % palette.size]
}
