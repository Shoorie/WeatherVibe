package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.weather.model.Condition

@Immutable
internal data class DayEntryUiState(
  val id: Long,
  val timeLabel: String,
  val rating: Int,
  val condition: Condition,
  val temperatureRounded: Int,
  val note: String?
)
