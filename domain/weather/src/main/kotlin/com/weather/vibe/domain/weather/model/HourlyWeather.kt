package com.weather.vibe.domain.weather.model

import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeather(
  val apparentTemperature: Double = 0.0,
  val cloudCover: Int = 0,
  val condition: WeatherCondition,
  val dewPoint: Double = 0.0,
  val humidity: Int,
  val precipitation: Double = 0.0,
  val precipitationProbability: Int,
  val surfacePressure: Double = 0.0,
  val temperature: Double,
  val time: String,
  val visibility: Double = 0.0,
  val windGusts: Double = 0.0,
  val windSpeed: Double
)
