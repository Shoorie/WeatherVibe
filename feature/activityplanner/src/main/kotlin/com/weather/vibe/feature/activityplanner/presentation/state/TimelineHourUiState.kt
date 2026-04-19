package com.weather.vibe.feature.activityplanner.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import java.time.LocalDateTime

@Immutable
internal data class TimelineHourUiState(
  val time: LocalDateTime,
  val hourLabel: String,
  val contentDescription: String,
  val score: Int,
  val tier: ScoreTier
)
