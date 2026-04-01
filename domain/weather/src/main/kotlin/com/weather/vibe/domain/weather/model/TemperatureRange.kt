package com.weather.vibe.domain.weather.model

enum class TemperatureRange(val label: String) {

  COLD("cold"),
  COOL("cool"),
  MILD("mild"),
  WARM("warm");

  companion object {

    private const val COLD_THRESHOLD = 5.0
    private const val COOL_THRESHOLD = 15.0
    private const val MILD_THRESHOLD = 23.0

    fun from(celsius: Double): TemperatureRange =
      when {
        celsius < COLD_THRESHOLD -> COLD
        celsius < COOL_THRESHOLD -> COOL
        celsius < MILD_THRESHOLD -> MILD
        else -> WARM
      }
  }
}
