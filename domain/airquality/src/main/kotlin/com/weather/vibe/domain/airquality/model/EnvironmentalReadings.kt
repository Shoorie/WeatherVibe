package com.weather.vibe.domain.airquality.model

data class EnvironmentalReadings(
  val airQuality: AirQuality?,
  val pollen: Pollen?
) {

  companion object {
    val Empty: EnvironmentalReadings =
      EnvironmentalReadings(airQuality = null, pollen = null)
  }
}
