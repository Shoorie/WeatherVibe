package com.weather.vibe.feature.viberating.preview

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay

@Immutable
internal data class VibeHistoryStatsPreviewParams(
  val averageDisplay: AverageRatingDisplay,
  val totalEntries: Int
)
