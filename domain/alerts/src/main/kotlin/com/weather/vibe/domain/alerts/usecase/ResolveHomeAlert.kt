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
    readings: EnvironmentalReadings,
    alertsEnabled: Boolean
  ): WeatherAlert? {

    if (!alertsEnabled) return null

    val aqiAlert = readings.airQuality?.let(detectAqiAlert::invoke)
    if (aqiAlert != null) return aqiAlert

    return readings.pollen?.let(detectPollenAlert::invoke)
  }
}
