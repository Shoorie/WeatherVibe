package com.weather.vibe.feature.viberating.presentation.history

import java.time.LocalDate

internal sealed interface VibeHistoryAction {
  data class DaySelected(val date: LocalDate) : VibeHistoryAction
  data object BackClick : VibeHistoryAction
  data object DayDetailDismissed : VibeHistoryAction
  data object NextMonthClick : VibeHistoryAction
  data object PreviousMonthClick : VibeHistoryAction
}
