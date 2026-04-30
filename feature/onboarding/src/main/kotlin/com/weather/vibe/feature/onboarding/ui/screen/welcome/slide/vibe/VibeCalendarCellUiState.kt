package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.runtime.Immutable

@Immutable
internal data class VibeCalendarCellUiState(
  val day: Int,
  val rating: Int
) {

  val isEmpty: Boolean get() = day == EMPTY_DAY

  companion object {
    const val NO_RATING = 0
    const val EMPTY_DAY = 0
    val EMPTY: VibeCalendarCellUiState =
      VibeCalendarCellUiState(day = EMPTY_DAY, rating = NO_RATING)
  }
}
