package com.weather.vibe.domain.location.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.repository.FavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.Instant

@Factory
class RefreshFavoritesWeather(
  private val favoriteRepository: FavoriteRepository,
  private val getWeather: GetWeather,
  private val snapshotRepository: LocationWeatherSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(forceAll: Boolean = false) {
    val favorites = favoriteRepository.observeFavorites().first()
    val now = Instant.ofEpochMilli(timeProvider.nowEpochMillis())
    val targets = filterStale(
      favorites = favorites,
      forceAll = forceAll,
      now = now
    )
    if (targets.isEmpty()) return
    refreshTargets(targets = targets, now = now)
  }

  private suspend fun filterStale(
    favorites: List<Favorite>,
    forceAll: Boolean,
    now: Instant
  ): List<Favorite> {
    if (forceAll) return favorites
    return favorites.filter { favorite ->
      val existing = snapshotRepository.findById(favorite.location.id)
      existing == null || isStale(updatedAt = existing.updatedAt, now = now)
    }
  }

  private suspend fun refreshTargets(
    targets: List<Favorite>,
    now: Instant
  ) {
    coroutineScope {
      targets
        .map { favorite -> async(Dispatchers.IO) { fetchAndStore(favorite = favorite, now = now) } }
        .awaitAll()
    }
  }

  private suspend fun fetchAndStore(
    favorite: Favorite,
    now: Instant
  ) {
    val result = getWeather(favorite.location.toCoordinates()).first()
    val weather = result.getOrNull() ?: return
    val snapshot = toSnapshot(
      locationId = favorite.location.id,
      data = weather,
      now = now
    )
    withContext(Dispatchers.IO) { snapshotRepository.save(snapshot = snapshot) }
  }

  private fun isStale(
    updatedAt: Instant,
    now: Instant
  ): Boolean = Duration.between(updatedAt, now) >= FRESHNESS_WINDOW

  private fun toSnapshot(
    locationId: Long,
    data: WeatherData,
    now: Instant
  ): LocationWeatherSnapshot {
    val today = data.dailyForecast.firstOrNull()
    return LocationWeatherSnapshot(
      condition = SimplifiedCondition.from(condition = data.condition),
      feelsLikeC = data.apparentTemperature,
      highC = today?.maxTemperature ?: data.currentTemperature,
      hourlyTemperaturesC = data.hourlyForecast.take(HOURLY_POINTS).map(HourlyWeather::temperature),
      humidityPercent = data.humidity,
      isDay = data.isDay,
      locationId = locationId,
      lowC = today?.minTemperature ?: data.currentTemperature,
      precipitationChancePercent = data.hourlyForecast.take(HOURLY_POINTS).maxOfOrNull { it.precipitationProbability } ?: 0,
      temperatureC = data.currentTemperature,
      updatedAt = now,
      windKph = data.windSpeed
    )
  }

  private companion object {
    val FRESHNESS_WINDOW: Duration = Duration.ofMinutes(30)
    const val HOURLY_POINTS = 24
  }
}
