package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GatherWeatherAlerts internal constructor(
  private val alertDeduplicator: AlertDeduplicator,
  private val areAlertsEnabled: AreAlertsEnabled,
  private val detectWeatherAlerts: DetectWeatherAlerts,
  private val getWeather: GetWeather,
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(): List<WeatherAlert> {

    if (!areAlertsEnabled()) return emptyList()

    val location = observeCurrentLocation().first() ?: return emptyList()
    val weather = weatherFor(location)

    return alertDeduplicator.filterFresh(detectWeatherAlerts(weather))
  }

  private suspend fun weatherFor(location: Location): WeatherData =
    getWeather(location.toCoordinates())
      .first()
      .getOrThrow()
}
