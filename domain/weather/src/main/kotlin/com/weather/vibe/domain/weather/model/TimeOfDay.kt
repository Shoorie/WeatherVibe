package com.weather.vibe.domain.weather.model

enum class TimeOfDay(val label: String) {

  AFTERNOON("afternoon"),
  EVENING("evening"),
  MORNING("morning"),
  NIGHT("night");

  companion object {

    private const val MORNING_START = 6
    private const val AFTERNOON_START = 11
    private const val EVENING_START = 17
    private const val NIGHT_START = 22

    fun from(hour: Int): TimeOfDay =
      when (hour) {
        in MORNING_START until AFTERNOON_START -> MORNING
        in AFTERNOON_START until EVENING_START -> AFTERNOON
        in EVENING_START until NIGHT_START -> EVENING
        else -> NIGHT
      }
  }
}
