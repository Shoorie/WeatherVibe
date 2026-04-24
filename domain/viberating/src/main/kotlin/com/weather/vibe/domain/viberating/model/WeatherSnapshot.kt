package com.weather.vibe.domain.viberating.model

data class WeatherSnapshot(
  val temperatureC: Double,
  val feelsLikeC: Double,
  val condition: Condition,
  val humidityPercent: Int,
  val windKph: Double,
  val pressureHpa: Int,
  val airQualityIndex: Int?,
  val pollenLevel: PollenLevel?
)
