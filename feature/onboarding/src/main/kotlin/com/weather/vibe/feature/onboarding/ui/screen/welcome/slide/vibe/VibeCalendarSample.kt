package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeCalendarCellUiState.Companion.EMPTY
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeCalendarCellUiState.Companion.NO_RATING

internal object VibeCalendarSample {

  const val WEEKS = 5
  const val DAYS_IN_WEEK = 7
  const val START_COLUMN = 2
  const val DAYS_IN_MONTH = 30
  const val TODAY = 30

  private val ratingsByDay: Map<Int, Int> = mapOf(
    1 to 3, 2 to 4, 3 to 5, 4 to 4, 5 to 3,
    7 to 2, 8 to 3, 10 to 4, 11 to 5,
    14 to 3, 15 to 4, 17 to 4, 18 to 3,
    21 to 4, 22 to 5, 24 to 5, 25 to 4,
    27 to 5, 28 to 5, 29 to 4, 30 to 5
  )

  fun cells(): List<VibeCalendarCellUiState> {

    val total = WEEKS * DAYS_IN_WEEK
    val cells = ArrayList<VibeCalendarCellUiState>(total)

    repeat(START_COLUMN) { cells += EMPTY }

    for (day in 1..DAYS_IN_MONTH) {
      val rating = ratingsByDay[day] ?: NO_RATING
      cells += VibeCalendarCellUiState(day = day, rating = rating)
    }

    while (cells.size < total) cells += EMPTY
    return cells
  }
}
