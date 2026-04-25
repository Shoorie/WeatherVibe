package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.weather.model.Condition
import java.time.LocalTime

@Immutable
internal data class DayEntryUiState(
  val id: Long,
  val time: LocalTime,
  val rating: Int,
  val condition: Condition,
  val temperatureC: Double,
  val note: String?
)
