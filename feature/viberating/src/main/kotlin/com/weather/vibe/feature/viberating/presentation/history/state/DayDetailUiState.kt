package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.viberating.model.Condition
import java.time.LocalDate

@Immutable
internal data class DayDetailUiState(
  val date: LocalDate,
  val rating: Int?,
  val condition: Condition?,
  val temperatureC: Double?
)
