package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rating.RatingColors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeCalendarCellUiState.Companion.NO_RATING

@Immutable
internal data class VibeCalendarTileStyle(
  val containerColor: Color,
  val contentColor: Color,
  val fontWeight: FontWeight
)

@Composable
@ReadOnlyComposable
internal fun rememberTileStyle(cell: VibeCalendarCellUiState): VibeCalendarTileStyle {

  val rated = cell.rating != NO_RATING
  val container = if (rated) RatingColors.forLevel(cell.rating) else colors.surfaceVariant
  val content = if (rated) Color.White else colors.onSurfaceVariant
  val weight = if (rated) FontWeight.Bold else FontWeight.SemiBold

  return VibeCalendarTileStyle(
    containerColor = container,
    contentColor = content,
    fontWeight = weight
  )
}
