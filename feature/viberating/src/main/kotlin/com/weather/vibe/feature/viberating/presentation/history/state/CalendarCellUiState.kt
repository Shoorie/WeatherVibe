package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
internal sealed interface CalendarCellUiState {

  data object Empty : CalendarCellUiState

  data class Day(
    val date: LocalDate,
    val dayOfMonth: Int,
    val rating: Int?,
    val isToday: Boolean,
    val isFuture: Boolean,
    val isSelected: Boolean
  ) : CalendarCellUiState
}
