package com.weather.vibe.feature.viberating.presentation.history

import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.domain.viberating.model.ConditionAverage
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.VibeStats
import com.weather.vibe.feature.viberating.presentation.history.state.AverageRatingDisplay
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.presentation.history.state.ConditionRankingUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayCellDescription
import com.weather.vibe.feature.viberating.presentation.history.state.DayDetailUiState
import com.weather.vibe.feature.viberating.presentation.history.state.DayEntryUiState
import com.weather.vibe.feature.viberating.presentation.history.state.PatternsSectionUiState
import com.weather.vibe.feature.viberating.presentation.history.state.VibeHistoryUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.core.annotation.Factory
import java.time.DayOfWeek.MONDAY
import java.time.Instant.ofEpochMilli
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale
import kotlin.math.roundToInt

@Factory
internal class VibeHistoryStateFactory {

  fun create(
    entriesByDate: Map<LocalDate, List<RatingEntry>>,
    allTimeStats: VibeStats,
    monthStats: VibeStats,
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
      averageDisplay = averageDisplay(stats = monthStats),
      totalEntries = monthStats.totalEntries,
      cells = cells,
      selectedDayDetail = selectedDate
        ?.let { date -> dayDetail(date = date, entries = entriesByDate[date].orEmpty()) },
      patterns = patternsSection(stats = allTimeStats)
    )
  }

  private fun averageDisplay(stats: VibeStats): AverageRatingDisplay =
    when {
      stats.averageRating > 0.0 -> AverageRatingDisplay.Available(
        value = stats.averageRating,
        ratingForColor = stats.averageRating.toInt()
          .coerceAtLeast(MIN_RATING)
      )
      else -> AverageRatingDisplay.Empty
    }

  private fun calendarCells(
    viewMonth: YearMonth,
    entriesByDate: Map<LocalDate, List<RatingEntry>>,
    today: LocalDate,
    selectedDate: LocalDate?
  ): ImmutableList<CalendarCellUiState> {

    val firstOfMonth = viewMonth.atDay(1)
    val daysInMonth = viewMonth.lengthOfMonth()
    val leadingEmpty = (firstOfMonth.dayOfWeek.value - MONDAY.value).mod(DAYS_IN_WEEK)

    val cells = buildList(CALENDAR_TOTAL_CELLS) {

      repeat(leadingEmpty) { add(Empty) }

      for (dayOfMonth in 1..daysInMonth) {
        val date = viewMonth.atDay(dayOfMonth)
        val dayEntries = entriesByDate[date].orEmpty()
        val averageRating = dayEntries.averageRatingOrNull()?.roundToInt()
        val isToday = date == today
        val isSelected = date == selectedDate
        add(
          Day(
            date = date,
            dayOfMonth = dayOfMonth,
            rating = averageRating,
            entryCount = dayEntries.size,
            isToday = isToday,
            isFuture = date.isAfter(today),
            isSelected = isSelected,
            description = DayCellDescription(
              dateLabel = date.format(CELL_DATE_FORMATTER),
              averageRating = averageRating,
              isToday = isToday,
              isSelected = isSelected
            )
          )
        )
      }
      while (size < CALENDAR_TOTAL_CELLS) add(Empty)
    }
    return cells.toImmutableList()
  }

  private fun dayDetail(date: LocalDate, entries: List<RatingEntry>): DayDetailUiState =
    DayDetailUiState(
      dateLabel = date.format(DAY_DETAIL_DATE_FORMATTER),
      entries = entries
        .sortedByDescending(RatingEntry::createdAtEpochMs)
        .map(::dayEntry)
        .toImmutableList()
    )

  private fun dayEntry(entry: RatingEntry): DayEntryUiState =
    DayEntryUiState(
      id = entry.id,
      timeLabel = ofEpochMilli(entry.createdAtEpochMs)
        .atZone(systemDefault())
        .toLocalTime()
        .format(ENTRY_TIME_FORMATTER),
      rating = entry.rating,
      condition = entry.weather.condition,
      temperatureRounded = entry.weather.temperatureC.roundToInt(),
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

    val maxAverage = stats.conditionAverages
      .maxOf(ConditionAverage::averageRating)

    return stats.conditionAverages
      .map { average ->
        ConditionRankingUiState(
          condition = average.condition,
          averageRating = average.averageRating,
          ratingForColor = average.averageRating.toInt().coerceAtLeast(MIN_RATING),
          entryCount = average.entryCount,
          progressFraction = (average.averageRating / maxAverage)
            .toFloat()
            .coerceIn(0f, 1f)
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

    private val CELL_DATE_FORMATTER: DateTimeFormatter = ofPattern("d MMMM")
    private val ENTRY_TIME_FORMATTER: DateTimeFormatter = ofPattern("HH:mm")
    private val DAY_DETAIL_DATE_FORMATTER: DateTimeFormatter =
      ofPattern("EEEE, d MMMM", Locale.forLanguageTag("pl"))
  }
}
