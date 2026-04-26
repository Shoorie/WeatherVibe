package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.YearMonth

@Immutable
internal data class VibeHistoryUiState(
  val viewMonth: YearMonth,
  val canNavigateNext: Boolean,
  val averageDisplay: AverageRatingDisplay,
  val totalEntries: Int,
  val cells: ImmutableList<CalendarCellUiState>,
  val selectedDayDetail: DayDetailUiState?,
  val patterns: PatternsSectionUiState
) {

  companion object {
    fun emptyFor(month: YearMonth): VibeHistoryUiState = VibeHistoryUiState(
      viewMonth = month,
      canNavigateNext = false,
      averageDisplay = AverageRatingDisplay.Empty,
      totalEntries = 0,
      cells = persistentListOf(),
      selectedDayDetail = null,
      patterns = PatternsSectionUiState.Hidden
    )
  }
}
