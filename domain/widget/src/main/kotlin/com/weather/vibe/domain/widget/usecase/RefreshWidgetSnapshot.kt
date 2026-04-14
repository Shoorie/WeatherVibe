package com.weather.vibe.domain.widget.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GenerateWeatherSuggestion
import com.weather.vibe.domain.weather.usecase.GetCurrentWeatherKey
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class RefreshWidgetSnapshot internal constructor(
  private val generateWeatherSuggestion: GenerateWeatherSuggestion,
  private val getCurrentWeatherKey: GetCurrentWeatherKey,
  private val getWeather: GetWeather,
  private val snapshotRepository: WidgetSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(location: Location): Result<WidgetSnapshot> {
    val weather = fetchWeather(location).getOrElse { return failure(it) }
    val suggestion = fetchSuggestion(weather).getOrElse { return failure(it) }
    val snapshot = snapshotOf(location, weather, suggestion)
    snapshotRepository.save(snapshot)
    return success(snapshot)
  }

  private suspend fun fetchWeather(location: Location): Result<WeatherData> =
    getWeather(location.toCoordinates()).first()

  private suspend fun fetchSuggestion(weather: WeatherData): Result<WeatherSuggestion> =
    generateWeatherSuggestion(weather, getCurrentWeatherKey(weather)).first()

  private fun snapshotOf(
    location: Location,
    weather: WeatherData,
    suggestion: WeatherSuggestion
  ): WidgetSnapshot =
    WidgetSnapshot(
      condition = weather.condition,
      currentTemperature = weather.currentTemperature,
      fetchedAtEpochMillis = timeProvider.nowEpochMillis(),
      isDay = weather.isDay,
      location = location,
      suggestion = suggestion
    )
}
