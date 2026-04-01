package com.weather.vibe.domain.weather.model

data class WeatherKey(
  val condition: SimplifiedCondition,
  val temperature: TemperatureRange,
  val timeOfDay: TimeOfDay
) {

  fun toHash(): String =
    "${condition.name}_${temperature.name}_${timeOfDay.name}"
}
