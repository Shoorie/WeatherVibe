package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay

internal class VibeHistoryStatsPreview : PreviewParameterProvider<VibeHistoryStatsPreviewParams> {

  private val populated: VibeHistoryStatsPreviewParams =
    VibeHistoryStatsPreviewParams(
      averageDisplay = AverageRatingDisplay.Available(value = 4.2, ratingForColor = 4),
      totalEntries = 17
    )

  private val empty: VibeHistoryStatsPreviewParams =
    VibeHistoryStatsPreviewParams(
      averageDisplay = AverageRatingDisplay.Empty,
      totalEntries = 0
    )

  override val values: Sequence<VibeHistoryStatsPreviewParams> =
    sequenceOf(populated, empty)
}
