package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.vibe.model.VibeMood
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.usecase.CalculateDailyVibe
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetCachedWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class RefreshWidgetSnapshot internal constructor(
  private val calculateDailyVibe: CalculateDailyVibe,
  private val getCachedWeatherSuggestion: GetCachedWeatherSuggestion,
  private val getCurrentWeatherKey: GetCurrentWeatherKey,
  private val getWeather: GetWeather,
  private val snapshotRepository: WidgetSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(location: Location): WidgetSnapshot {
    val weather = fetchWeather(location)
    val snapshot = snapshotOf(location, weather)
    snapshotRepository.save(snapshot)
    return snapshot
  }

  private suspend fun fetchWeather(location: Location): WeatherData =
    getWeather(location.toCoordinates())
      .first().getOrThrow()

  private suspend fun cachedMood(weather: WeatherData): String? =
    getCachedWeatherSuggestion(
      todayDispositionEntries = emptyList(),
      weatherData = weather,
      weatherKey = getCurrentWeatherKey(weather)
    )?.mood

  private fun vibeMood(weather: WeatherData): VibeMood =
    calculateDailyVibe(weather = weather, readings = EnvironmentalReadings.Empty)
      .getOrNull()
      ?.mood
      ?: OKAY

  private suspend fun snapshotOf(
    location: Location,
    weather: WeatherData
  ): WidgetSnapshot =
    WidgetSnapshot(
      aiMood = cachedMood(weather),
      condition = weather.condition,
      currentTemperature = weather.currentTemperature,
      fetchedAtEpochMillis = timeProvider.nowEpochMillis(),
      isDay = weather.isDay,
      location = location,
      vibeMood = vibeMood(weather)
    )
}
