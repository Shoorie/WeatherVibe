package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.settings.usecase.IsMorningBriefEnabled
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.briefTextOrNull
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GetMorningBriefText internal constructor(
  private val generateWeatherSuggestion: GenerateWeatherSuggestion,
  private val getCurrentWeatherKey: GetCurrentWeatherKey,
  private val getWeather: GetWeather,
  private val isMorningBriefEnabled: IsMorningBriefEnabled,
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(): String? {

    if (!isMorningBriefEnabled()) return null

    val location = observeCurrentLocation().first() ?: return null
    val weather = weatherFor(location)

    return briefFor(weather)
  }

  private suspend fun weatherFor(location: Location): WeatherData =
    getWeather(location.toCoordinates())
      .first()
      .getOrThrow()

  private suspend fun briefFor(weather: WeatherData): String? =
    generateWeatherSuggestion(
      weatherData = weather,
      weatherKey = getCurrentWeatherKey(weather)
    )
      .first()
      .getOrThrow()
      .briefTextOrNull()
}
