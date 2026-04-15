package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class RefreshWidgetSnapshot internal constructor(
  private val generateWeatherSuggestion: GenerateWeatherSuggestion,
  private val getCurrentWeatherKey: GetCurrentWeatherKey,
  private val getWeather: GetWeather,
  private val snapshotRepository: WidgetSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(location: Location): WidgetSnapshot {
    val weather = fetchWeather(location)
    val mood = fetchMood(weather)
    val snapshot = snapshotOf(location, weather, mood)
    snapshotRepository.save(snapshot)
    return snapshot
  }

  private suspend fun fetchWeather(location: Location): WeatherData =
    getWeather(location.toCoordinates()).first().getOrThrow()

  private suspend fun fetchMood(weather: WeatherData): String =
    generateWeatherSuggestion(weather, getCurrentWeatherKey(weather))
      .first().getOrThrow().mood

  private fun snapshotOf(
    location: Location,
    weather: WeatherData,
    mood: String
  ): WidgetSnapshot =
    WidgetSnapshot(
      condition = weather.condition,
      currentTemperature = weather.currentTemperature,
      fetchedAtEpochMillis = timeProvider.nowEpochMillis(),
      location = location,
      mood = mood
    )
}
