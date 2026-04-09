package com.weather.vibe.domain.weather.model

data class WeatherMetrics(
  val cloudCover: Int,
  val dewPoint: Double,
  val humidity: Int,
  val precipitationProbability: Int,
  val precipitationSum: Double,
  val surfacePressure: Double,
  val uvIndexMax: Double,
  val visibility: Double,
  val windDirection: WindDirection,
  val windGusts: Double,
  val windSpeed: Double,
  val windSpeedMax: Double
)
