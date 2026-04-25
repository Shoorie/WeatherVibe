package com.weather.vibe.feature.viberating.ui.history

import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay.Available

internal object VibeHistoryStatsPreviewParams {

  val LoadedAverage: AverageRatingDisplay =
    Available(value = 4.2, ratingForColor = 4)

  const val LoadedTotal: Int = 17
}
