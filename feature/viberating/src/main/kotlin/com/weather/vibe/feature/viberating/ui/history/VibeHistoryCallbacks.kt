package com.weather.vibe.feature.viberating.ui.history

import java.time.LocalDate

internal data class VibeHistoryCallbacks(
  val onBackClicked: () -> Unit,
  val onPreviousMonthClicked: () -> Unit,
  val onNextMonthClicked: () -> Unit,
  val onDayClicked: (LocalDate) -> Unit,
  val onDayDetailDismissed: () -> Unit
) {
  companion object {
    val Noop: VibeHistoryCallbacks = VibeHistoryCallbacks(
      onBackClicked = {},
      onPreviousMonthClicked = {},
      onNextMonthClicked = {},
      onDayClicked = {},
      onDayDetailDismissed = {}
    )
  }
}
