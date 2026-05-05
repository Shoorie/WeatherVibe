package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.alerts.model.WeatherAlert
import org.koin.core.annotation.Factory

@Factory
class ResolveHomeAlert internal constructor(
  private val detectAqiAlert: DetectAqiAlert,
  private val detectPollenAlert: DetectPollenAlert
) {

  operator fun invoke(
    pollenAlertsEnabled: Boolean,
    readings: EnvironmentalReadings,
    weatherAlertsEnabled: Boolean
  ): WeatherAlert? =
    airQualityAlert(readings = readings, weatherAlertsEnabled = weatherAlertsEnabled)
      ?: pollenAlert(readings = readings, pollenAlertsEnabled = pollenAlertsEnabled)

  private fun airQualityAlert(
    readings: EnvironmentalReadings,
    weatherAlertsEnabled: Boolean
  ): WeatherAlert? =
    when {
      weatherAlertsEnabled -> readings.airQuality?.let(detectAqiAlert::invoke)
      else -> null
    }

  private fun pollenAlert(
    readings: EnvironmentalReadings,
    pollenAlertsEnabled: Boolean
  ): WeatherAlert? =
    when {
      pollenAlertsEnabled -> readings.pollen?.let(detectPollenAlert::invoke)
      else -> null
    }
}
