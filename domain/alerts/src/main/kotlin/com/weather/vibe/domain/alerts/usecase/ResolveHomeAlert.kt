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
    airQualityAlert(readings, weatherAlertsEnabled)
      ?: pollenAlert(readings, pollenAlertsEnabled)

  private fun airQualityAlert(
    readings: EnvironmentalReadings,
    weatherAlertsEnabled: Boolean
  ): WeatherAlert? =
    when (weatherAlertsEnabled) {
      true -> readings.airQuality?.let(detectAqiAlert::invoke)
      false -> null
    }

  private fun pollenAlert(
    readings: EnvironmentalReadings,
    pollenAlertsEnabled: Boolean
  ): WeatherAlert? =
    when (pollenAlertsEnabled) {
      true -> readings.pollen?.let(detectPollenAlert::invoke)
      false -> null
    }
}
