package com.weather.vibe.feature.viberating.presentation.history.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.YearMonth

@Immutable
internal data class VibeHistoryUiState(
  val viewMonth: YearMonth,
  val canNavigateNext: Boolean,
  val averageRating: Double,
  val totalEntries: Int,
  val cells: ImmutableList<CalendarCellUiState>,
  val selectedDayDetail: DayDetailUiState?,
  val conditionRanking: ImmutableList<ConditionRankingUiState>
) {

  companion object {
    fun emptyFor(month: YearMonth): VibeHistoryUiState = VibeHistoryUiState(
      viewMonth = month,
      canNavigateNext = false,
      averageRating = 0.0,
      totalEntries = 0,
      cells = persistentListOf(),
      selectedDayDetail = null,
      conditionRanking = persistentListOf()
    )
  }
}
