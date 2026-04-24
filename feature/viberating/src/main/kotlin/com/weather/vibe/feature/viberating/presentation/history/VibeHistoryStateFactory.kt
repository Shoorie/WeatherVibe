package com.weather.vibe.feature.viberating.presentation.history

import com.weather.vibe.domain.viberating.model.ConditionAverage
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Factory
internal class VibeHistoryStateFactory {

  fun create(
    entriesByDate: Map<LocalDate, RatingEntry>,
    stats: VibeStats,
    viewMonth: YearMonth,
    currentMonth: YearMonth,
    today: LocalDate,
    selectedDate: LocalDate?
  ): VibeHistoryUiState {
    val cells = calendarCells(
      viewMonth = viewMonth,
      entriesByDate = entriesByDate,
      today = today,
      selectedDate = selectedDate
    )
    return VibeHistoryUiState(
      viewMonth = viewMonth,
      canNavigateNext = viewMonth < currentMonth,
      averageRating = stats.averageRating,
      totalEntries = stats.totalEntries,
      cells = cells,
      selectedDayDetail = selectedDate?.let { dayDetail(it, entriesByDate[it]) },
      conditionRanking = conditionRanking(stats)
    )
  }

  private fun calendarCells(
    viewMonth: YearMonth,
    entriesByDate: Map<LocalDate, RatingEntry>,
    today: LocalDate,
    selectedDate: LocalDate?
  ): ImmutableList<CalendarCellUiState> {
    val firstOfMonth = viewMonth.atDay(1)
    val daysInMonth = viewMonth.lengthOfMonth()
    val leadingEmpty = (firstOfMonth.dayOfWeek.value - DayOfWeek.MONDAY.value).mod(DAYS_IN_WEEK)
    val cells = buildList(CALENDAR_TOTAL_CELLS) {
      repeat(leadingEmpty) { add(Empty) }
      for (dayOfMonth in 1..daysInMonth) {
        val date = viewMonth.atDay(dayOfMonth)
        add(
          Day(
            date = date,
            dayOfMonth = dayOfMonth,
            rating = entriesByDate[date]?.rating,
            isToday = date == today,
            isFuture = date.isAfter(today),
            isSelected = date == selectedDate
          )
        )
      }
      while (size < CALENDAR_TOTAL_CELLS) add(Empty)
    }
    return cells.toImmutableList()
  }

  private fun dayDetail(date: LocalDate, entry: RatingEntry?): DayDetailUiState =
    DayDetailUiState(
      date = date,
      rating = entry?.rating,
      condition = entry?.weather?.condition,
      temperatureC = entry?.weather?.temperatureC
    )

  private fun conditionRanking(stats: VibeStats): ImmutableList<ConditionRankingUiState> {
    val maxAverage = stats.conditionAverages.maxOfOrNull(ConditionAverage::averageRating) ?: 1.0
    return stats.conditionAverages
      .map { average ->
        ConditionRankingUiState(
          condition = average.condition,
          averageRating = average.averageRating,
          entryCount = average.entryCount,
          progressFraction = (average.averageRating / maxAverage).toFloat().coerceIn(0f, 1f)
        )
      }
      .toImmutableList()
  }

  companion object {
    private const val DAYS_IN_WEEK: Int = 7
    private const val CALENDAR_TOTAL_CELLS: Int = 42
  }
}
