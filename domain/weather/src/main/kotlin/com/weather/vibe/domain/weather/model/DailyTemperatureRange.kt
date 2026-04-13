package com.weather.vibe.domain.weather.model

import java.time.LocalDate

data class DailyTemperatureRange(
  val date: LocalDate,
  val displayedMin: Int,
  val displayedMax: Int,
  val startFraction: Float,
  val endFraction: Float,
  val currentFraction: Float?
) {

  companion object {
    fun emptyFor(date: LocalDate) =
      DailyTemperatureRange(
        date = date,
        displayedMin = 0,
        displayedMax = 0,
        startFraction = 0f,
        endFraction = 0f,
        currentFraction = null
      )
  }
}
