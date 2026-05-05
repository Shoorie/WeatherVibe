package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.alerts.dedupe.AlertDeduplicator
import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Factory

@Factory
class GatherWeatherAlerts internal constructor(
  private val alertDeduplicator: AlertDeduplicator,
  private val detectAqiAlert: DetectAqiAlert,
  private val detectUvAlert: DetectUvAlert,
  private val detectWeatherAlerts: DetectWeatherAlerts,
  private val getAirQuality: GetAirQuality,
  private val getWeather: GetWeather,
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(): List<WeatherAlert> {
    val coordinates = currentCoordinates() ?: return emptyList()
    val weather = currentWeather(coordinates) ?: return emptyList()
    val airQuality = getAirQuality(coordinates).getOrNull()
    return alertDeduplicator.filterFresh(candidates(weather = weather, airQuality = airQuality))
  }

  private fun candidates(weather: WeatherData, airQuality: AirQuality?): List<WeatherAlert> =
    detectWeatherAlerts(weather) + listOfNotNull(
      airQuality?.let(detectAqiAlert::invoke),
      detectUvAlert(weather)
    )

  private suspend fun currentCoordinates(): Coordinates? =
    observeCurrentLocation().firstOrNull()?.toCoordinates()

  private suspend fun currentWeather(coordinates: Coordinates): WeatherData? =
    getWeather(coordinates).firstOrNull()?.getOrNull()
}
