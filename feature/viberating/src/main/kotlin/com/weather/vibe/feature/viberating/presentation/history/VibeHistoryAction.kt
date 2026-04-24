package com.weather.vibe.feature.viberating.presentation.history

import java.time.LocalDate
import java.time.YearMonth

internal sealed interface VibeHistoryAction {

  data object PreviousMonthClick : VibeHistoryAction

  data object NextMonthClick : VibeHistoryAction

  data class DaySelected(val date: LocalDate) : VibeHistoryAction

  data object DayDetailDismissed : VibeHistoryAction

  data object BackClick : VibeHistoryAction
}

internal data class VibeHistoryRange(
  val viewMonth: YearMonth,
  val currentMonth: YearMonth
) {
  val canNavigateNext: Boolean = viewMonth < currentMonth
}
