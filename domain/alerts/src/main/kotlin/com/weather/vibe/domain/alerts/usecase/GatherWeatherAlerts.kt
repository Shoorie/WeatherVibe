package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GatherWeatherAlerts internal constructor(
  private val alertDeduplicator: AlertDeduplicator,
  private val areAlertsEnabled: AreAlertsEnabled,
  private val detectors: AlertDetectors,
  private val sources: AlertSources
) {

  suspend operator fun invoke(): List<WeatherAlert> {

    if (!areAlertsEnabled()) return emptyList()

    val location = sources.observeCurrentLocation().first() ?: return emptyList()
    val coordinates = location.toCoordinates()

    val weather = sources.getWeather(coordinates).first().getOrThrow()
    val (airQuality, pollen) = coroutineScope {
      val airQualityReading = async { sources.getAirQuality(coordinates) }
      val pollenReading = async { sources.getPollen(coordinates) }
      airQualityReading.await().getOrNull() to pollenReading.await().getOrNull()
    }

    val aqiAlert = airQuality?.let(detectors.detectAqiAlert::invoke)
    val pollenAlert = pollen?.let(detectors.detectPollenAlert::invoke)
    val alerts = detectors.detectWeatherAlerts(weather) + listOfNotNull(aqiAlert, pollenAlert)

    return alertDeduplicator.filterFresh(alerts)
  }
}
