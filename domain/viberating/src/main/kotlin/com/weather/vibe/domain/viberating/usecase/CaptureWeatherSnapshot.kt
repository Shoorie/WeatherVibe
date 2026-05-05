package com.weather.vibe.domain.viberating.usecase

import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.viberating.mapper.WeatherDataToVibeSnapshot
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import com.weather.vibe.domain.weather.model.Coordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Factory

@Factory
class CaptureWeatherSnapshot internal constructor(
  private val getWeather: GetWeather,
  private val observeCurrentLocation: ObserveCurrentLocation,
  private val weatherDataToVibeSnapshot: WeatherDataToVibeSnapshot
) {

  suspend operator fun invoke(): WeatherSnapshot {
    val coordinates = currentCoordinates() ?: return WeatherSnapshot.Unknown
    val weather = currentWeather(coordinates) ?: return WeatherSnapshot.Unknown
    return weatherDataToVibeSnapshot.map(weather)
  }

  private suspend fun currentCoordinates(): Coordinates? =
    observeCurrentLocation().firstOrNull()?.toCoordinates()

  private suspend fun currentWeather(coordinates: Coordinates): WeatherData? =
    getWeather(coordinates).firstOrNull()?.getOrNull()
}
