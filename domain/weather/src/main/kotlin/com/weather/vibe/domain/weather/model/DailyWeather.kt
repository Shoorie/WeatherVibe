package com.weather.vibe.domain.weather.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyWeather(
  val condition: WeatherCondition,
  val date: String,
  val maxTemperature: Double,
  val minTemperature: Double,
  val precipitationProbability: Int,
  val precipitationSum: Double = 0.0,
  val sunrise: String = "",
  val sunset: String = "",
  val uvIndexMax: Double = 0.0,
  val windGustsMax: Double = 0.0,
  val windSpeedMax: Double = 0.0
)
