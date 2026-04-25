package com.weather.vibe.feature.viberating.presentation.history

import com.weather.vibe.domain.viberating.model.ConditionAverage
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayEntryUiState
import com.weather.vibe.feature.viberating.presentation.history.state.PatternsSectionUiState
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import kotlin.math.roundToInt

@Factory
internal class VibeHistoryStateFactory {

  fun create(
    entriesByDate: Map<LocalDate, List<RatingEntry>>,
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
      selectedDayDetail = selectedDate?.let { date ->
        dayDetail(date = date, entries = entriesByDate[date].orEmpty())
      },
      patterns = patternsSection(stats = stats)
    )
  }

  private fun calendarCells(
    viewMonth: YearMonth,
    entriesByDate: Map<LocalDate, List<RatingEntry>>,
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
        val dayEntries = entriesByDate[date].orEmpty()
        add(
          Day(
            date = date,
            dayOfMonth = dayOfMonth,
            rating = dayEntries.averageRatingOrNull()?.roundToInt(),
            entryCount = dayEntries.size,
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

  private fun dayDetail(date: LocalDate, entries: List<RatingEntry>): DayDetailUiState =
    DayDetailUiState(
      date = date,
      entries = entries
        .sortedByDescending(RatingEntry::createdAtEpochMs)
        .map(::dayEntry)
        .toImmutableList()
    )

  private fun dayEntry(entry: RatingEntry): DayEntryUiState =
    DayEntryUiState(
      id = entry.id,
      time = Instant.ofEpochMilli(entry.createdAtEpochMs).atZone(ZoneId.systemDefault()).toLocalTime(),
      rating = entry.rating,
      condition = entry.weather.condition,
      temperatureC = entry.weather.temperatureC,
      note = entry.note?.takeIf { it.isNotBlank() }
    )

  private fun patternsSection(stats: VibeStats): PatternsSectionUiState =
    when {
      stats.totalEntries == 0 -> PatternsSectionUiState.Hidden
      stats.totalEntries < PATTERNS_UNLOCK_THRESHOLD -> PatternsSectionUiState.Locked(
        entriesNeeded = PATTERNS_UNLOCK_THRESHOLD - stats.totalEntries,
        entriesSoFar = stats.totalEntries,
        unlockThreshold = PATTERNS_UNLOCK_THRESHOLD
      )
      else -> PatternsSectionUiState.Unlocked(
        ranking = conditionRanking(stats),
        basedOnEntries = stats.totalEntries
      )
    }

  private fun conditionRanking(stats: VibeStats): ImmutableList<ConditionRankingUiState> {
    val maxAverage = stats.conditionAverages.maxOf(ConditionAverage::averageRating)
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

  private fun List<RatingEntry>.averageRatingOrNull(): Double? =
    if (isEmpty()) null else map(RatingEntry::rating).average()

  companion object {
    const val PATTERNS_UNLOCK_THRESHOLD: Int = 14
    private const val DAYS_IN_WEEK: Int = 7
    private const val CALENDAR_TOTAL_CELLS: Int = 42
  }
}
