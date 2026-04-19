package com.weather.vibe.feature.activityplanner.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.activityplanner.model.ScoreTier.EXCELLENT
import com.weather.vibe.domain.activityplanner.model.ScoreTier.POOR
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import java.time.LocalDateTime

internal class TimelineHourPreview : PreviewParameterProvider<TimelineHourUiState> {

  private val highScoredHour: TimelineHourUiState =
    TimelineHourUiState(
      time = LocalDateTime.of(2026, 4, 13, 14, 0),
      hourLabel = "14",
      contentDescription = "14 o'clock, Excellent, score 88",
      score = 88,
      tier = EXCELLENT
    )

  private val lowScoredHour: TimelineHourUiState =
    TimelineHourUiState(
      time = LocalDateTime.of(2026, 4, 13, 21, 0),
      hourLabel = "21",
      contentDescription = "21 o'clock, Poor, score 22",
      score = 22,
      tier = POOR
    )

  override val values: Sequence<TimelineHourUiState> =
    sequenceOf(highScoredHour, lowScoredHour)
}
