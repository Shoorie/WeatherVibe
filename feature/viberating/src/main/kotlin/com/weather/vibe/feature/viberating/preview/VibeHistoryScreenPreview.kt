package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.Condition.CLOUDY
import com.weather.vibe.domain.weather.model.Condition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.Condition.RAIN
import com.weather.vibe.domain.weather.model.Condition.SUNNY
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.YearMonth

internal class VibeHistoryScreenPreview : PreviewParameterProvider<VibeHistoryUiState> {

  private val previewMonth: YearMonth = YearMonth.of(PREVIEW_YEAR, PREVIEW_MONTH)
  private val today: LocalDate = previewMonth.atDay(PREVIEW_TODAY_DAY)

  private val emptyState: VibeHistoryUiState =
    VibeHistoryUiState.emptyFor(previewMonth)

  private val lockedPatterns: VibeHistoryUiState =
    VibeHistoryUiState(
      viewMonth = previewMonth,
      canNavigateNext = false,
      averageDisplay = AverageRatingDisplay.Available(value = 3.6, ratingForColor = 3),
      totalEntries = 6,
      cells = populatedCells(selectedDay = null),
      selectedDayDetail = null,
      patterns = PatternsSectionUiState.Locked(
        entriesNeeded = 8,
        entriesSoFar = 6,
        unlockThreshold = 14
      )
    )

  private val unlockedPatternsWithSelectedDay: VibeHistoryUiState =
    VibeHistoryUiState(
      viewMonth = previewMonth,
      canNavigateNext = false,
      averageDisplay = AverageRatingDisplay.Available(value = 4.1, ratingForColor = 4),
      totalEntries = 28,
      cells = populatedCells(selectedDay = PREVIEW_SELECTED_DAY),
      selectedDayDetail = selectedDayDetail(),
      patterns = PatternsSectionUiState.Unlocked(
        ranking = ranking(),
        basedOnEntries = 28
      )
    )

  override val values: Sequence<VibeHistoryUiState> =
    sequenceOf(emptyState, lockedPatterns, unlockedPatternsWithSelectedDay)

  private fun populatedCells(selectedDay: Int?): ImmutableList<CalendarCellUiState> = buildList {
    repeat(LEADING_EMPTY) { add(Empty) }
    val daysInMonth = previewMonth.lengthOfMonth()
    for (dayOfMonth in 1..daysInMonth) {
      val date = previewMonth.atDay(dayOfMonth)
      val rating = ratingForDay(dayOfMonth)
      val isToday = date == today
      val isSelected = dayOfMonth == selectedDay
      add(
        Day(
          date = date,
          dayOfMonth = dayOfMonth,
          rating = rating,
          entryCount = if (rating != null) 1 else 0,
          isToday = isToday,
          isFuture = date.isAfter(today),
          isSelected = isSelected,
          description = DayCellDescription(
            dateLabel = "$dayOfMonth",
            averageRating = rating,
            isToday = isToday,
            isSelected = isSelected
          )
        )
      )
    }
    while (size < TOTAL_CELLS) add(Empty)
  }.toImmutableList()

  private fun ratingForDay(dayOfMonth: Int): Int? = when {
    dayOfMonth > PREVIEW_TODAY_DAY -> null
    dayOfMonth % UNRATED_PERIOD == 0 -> null
    else -> ((dayOfMonth - 1) % MAX_RATING) + 1
  }

  private fun selectedDayDetail(): DayDetailUiState =
    DayDetailUiState(
      dateLabel = "Środa, $PREVIEW_SELECTED_DAY kwietnia",
      entries = persistentListOf(
        DayEntryUiState(
          id = 1,
          timeLabel = "08:30",
          rating = 5,
          condition = SUNNY,
          temperatureRounded = 18,
          note = null
        ),
        DayEntryUiState(
          id = 2,
          timeLabel = "14:15",
          rating = 4,
          condition = PARTLY_CLOUDY,
          temperatureRounded = 22,
          note = "Świetny spacer w parku"
        )
      )
    )

  private fun ranking(): ImmutableList<ConditionRankingUiState> = persistentListOf(
    ConditionRankingUiState(
      condition = SUNNY,
      averageRating = 4.6,
      ratingForColor = 4,
      entryCount = 12,
      progressFraction = 1f
    ),
    ConditionRankingUiState(
      condition = PARTLY_CLOUDY,
      averageRating = 3.8,
      ratingForColor = 3,
      entryCount = 8,
      progressFraction = 0.83f
    ),
    ConditionRankingUiState(
      condition = CLOUDY,
      averageRating = 2.9,
      ratingForColor = 2,
      entryCount = 5,
      progressFraction = 0.63f
    ),
    ConditionRankingUiState(
      condition = RAIN,
      averageRating = 1.8,
      ratingForColor = 1,
      entryCount = 3,
      progressFraction = 0.39f
    )
  )

  private companion object {
    const val PREVIEW_YEAR: Int = 2026
    const val PREVIEW_MONTH: Int = 4
    const val PREVIEW_TODAY_DAY: Int = 24
    const val PREVIEW_SELECTED_DAY: Int = 22
    const val LEADING_EMPTY: Int = 2
    const val TOTAL_CELLS: Int = 42
    const val UNRATED_PERIOD: Int = 5
    const val MAX_RATING: Int = 5
  }
}
