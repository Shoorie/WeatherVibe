package com.weather.vibe.feature.viberating.ui.history.defaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay.Available
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay.Empty
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.averageEmpty
import com.weather.vibe.feature.viberating.ui.history.defaults.VibeHistoryStatsDefaults.AverageFormat

internal object VibeHistoryStatsStyles {

  @Composable
  fun averageText(display: AverageRatingDisplay): String = when (display) {
    is Available -> AverageFormat.format(display.value)
    Empty -> averageEmpty()
  }

  @Composable
  fun averageColor(display: AverageRatingDisplay): Color = when (display) {
    is Available -> ratingColor(display.ratingForColor)
    Empty -> colors.onSurface
  }
}
