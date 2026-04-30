package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.TILE_ASPECT_RATIO
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.TileRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.TodayBorderWidth

@Composable
internal fun VibeCalendarTile(
  modifier: Modifier = Modifier,
  cell: VibeCalendarCellUiState,
  isToday: Boolean
) {
  if (cell.isEmpty) {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .aspectRatio(TILE_ASPECT_RATIO)
    )
    return
  }
  TileSurface(
    modifier = modifier,
    cell = cell,
    isToday = isToday
  )
}

@Composable
private fun TileSurface(
  modifier: Modifier,
  cell: VibeCalendarCellUiState,
  isToday: Boolean
) {

  val style = rememberTileStyle(cell = cell)
  val tileShape = remember { RoundedCornerShape(TileRadius) }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .aspectRatio(TILE_ASPECT_RATIO)
      .clip(tileShape)
      .background(style.containerColor)
      .then(todayBorder(isToday = isToday, shape = tileShape)),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = cell.day.toString(),
      style = typography.labelMedium
        .copy(fontWeight = style.fontWeight),
      color = style.contentColor
    )
  }
}

@Composable
private fun todayBorder(
  isToday: Boolean,
  shape: RoundedCornerShape
): Modifier {
  if (!isToday) return Modifier
  return Modifier
    .border(
      width = TodayBorderWidth,
      color = colors.accent,
      shape = shape
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeCalendarTile(
      cell = VibeCalendarCellUiState(day = 22, rating = 5),
      isToday = true
    )
  }
}
