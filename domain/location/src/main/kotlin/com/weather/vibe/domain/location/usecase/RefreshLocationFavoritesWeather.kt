package com.weather.vibe.domain.location.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.mapper.WeatherDataToSnapshotMapper
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import java.time.Instant
import java.time.Instant.ofEpochMilli

@Factory
class RefreshLocationFavoritesWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val getWeather: GetWeather,
  private val snapshotMapper: WeatherDataToSnapshotMapper,
  private val snapshotRepository: LocationWeatherSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke() {

    val favorites = favoriteRepository
      .observeFavorites().first()

    if (favorites.isEmpty()) return

    val capturedAt = ofEpochMilli(timeProvider.nowEpochMillis())
    refreshInParallel(favorites, capturedAt)
  }

  private suspend fun refreshInParallel(
    favorites: List<LocationFavorite>,
    capturedAt: Instant
  ) = coroutineScope {
    favorites
      .map { favorite ->
        async(IO) {
          fetchAndPersistWeatherFor(
            favorite = favorite,
            capturedAt = capturedAt
          )
        }
      }
      .awaitAll()
  }

  private suspend fun fetchAndPersistWeatherFor(
    favorite: LocationFavorite,
    capturedAt: Instant
  ) {
    val coordinates = favorite.location.toCoordinates()
    val weather = getWeather(coordinates).first().getOrNull() ?: return
    val snapshot = snapshotMapper.toSnapshot(
      locationId = favorite.location.id,
      data = weather,
      capturedAt = capturedAt
    )
    snapshotRepository.save(snapshot = snapshot)
  }
}
