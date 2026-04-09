package com.weather.vibe.domain.weather.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyWeather(
  val condition: WeatherCondition,
  val date: LocalDate,
  val maxTemperature: Double,
  val minTemperature: Double,
  val precipitationProbability: Int,
  val precipitationSum: Double = 0.0,
  val sunrise: LocalDateTime? = null,
  val sunset: LocalDateTime? = null,
  val uvIndexMax: Double = 0.0,
  val windGustsMax: Double = 0.0,
  val windSpeedMax: Double = 0.0
)
