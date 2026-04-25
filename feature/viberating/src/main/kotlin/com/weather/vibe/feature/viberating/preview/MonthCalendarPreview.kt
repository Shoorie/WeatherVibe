package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Day
import com.weather.vibe.feature.viberating.presentation.history.state.CalendarCellUiState.Empty
import com.weather.vibe.feature.viberating.presentation.history.state.DayCellDescription
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.YearMonth

internal class MonthCalendarPreview : PreviewParameterProvider<MonthCalendarPreviewParams> {

  private val previewMonth: YearMonth = YearMonth.of(PREVIEW_YEAR, PREVIEW_MONTH)
  private val today: LocalDate = previewMonth.atDay(PREVIEW_TODAY_DAY)

  private val populatedMonth: MonthCalendarPreviewParams =
    MonthCalendarPreviewParams(
      viewMonth = previewMonth,
      canNavigateNext = false,
      cells = buildPopulatedMonth()
    )

  override val values: Sequence<MonthCalendarPreviewParams> =
    sequenceOf(populatedMonth)

  private fun buildPopulatedMonth(): ImmutableList<CalendarCellUiState> = buildList {
    repeat(LEADING_EMPTY) { add(Empty) }
    val daysInMonth = previewMonth.lengthOfMonth()
    for (dayOfMonth in 1..daysInMonth) {
      val date = previewMonth.atDay(dayOfMonth)
      val rating = ratingForDay(dayOfMonth)
      add(
        Day(
          date = date,
          dayOfMonth = dayOfMonth,
          rating = rating,
          entryCount = if (rating != null) 1 else 0,
          isToday = date == today,
          isFuture = date.isAfter(today),
          isSelected = false,
          description = DayCellDescription(
            dateLabel = "$dayOfMonth",
            averageRating = rating,
            isToday = date == today,
            isSelected = false
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

  private companion object {
    const val PREVIEW_YEAR: Int = 2026
    const val PREVIEW_MONTH: Int = 4
    const val PREVIEW_TODAY_DAY: Int = 24
    const val LEADING_EMPTY: Int = 2
    const val TOTAL_CELLS: Int = 42
    const val UNRATED_PERIOD: Int = 5
    const val MAX_RATING: Int = 5
  }
}
