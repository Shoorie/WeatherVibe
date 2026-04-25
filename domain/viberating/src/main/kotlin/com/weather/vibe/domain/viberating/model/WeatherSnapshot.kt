package com.weather.vibe.domain.viberating.model

import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.weather.model.Condition

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
