package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.AreAlertsEnabled
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GatherWeatherAlerts internal constructor(
  private val alertDeduplicator: AlertDeduplicator,
  private val areAlertsEnabled: AreAlertsEnabled,
  private val detectAqiAlert: DetectAqiAlert,
  private val detectWeatherAlerts: DetectWeatherAlerts,
  private val getAirQuality: GetAirQuality,
  private val getWeather: GetWeather,
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(): List<WeatherAlert> {

    if (!areAlertsEnabled()) return emptyList()

    val location = observeCurrentLocation().first() ?: return emptyList()
    val coordinates = location.toCoordinates()

    val weather = weatherFor(location)
    val airQuality = airQualityFor(coordinates)
    val aqiAlert = airQuality?.let(detectAqiAlert::invoke)
    val alerts = detectWeatherAlerts(weather) + listOfNotNull(aqiAlert)

    return alertDeduplicator.filterFresh(alerts)
  }

  private suspend fun weatherFor(location: Location): WeatherData =
    getWeather(location.toCoordinates())
      .first()
      .getOrThrow()

  private suspend fun airQualityFor(coordinates: Coordinates): AirQuality? =
    try {
      getAirQuality(coordinates)
    } catch (cancellation: CancellationException) {
      throw cancellation
    } catch (_: Throwable) {
      null
    }
}
