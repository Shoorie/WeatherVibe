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
) {

  companion object {

    val Unknown: WeatherSnapshot = WeatherSnapshot(
      temperatureC = 0.0,
      feelsLikeC = 0.0,
      condition = Condition.CLOUDY,
      humidityPercent = 0,
      windKph = 0.0,
      pressureHpa = 0,
      airQualityIndex = null,
      pollenLevel = null
    )
  }
}
