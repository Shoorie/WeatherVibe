package com.weather.vibe.domain.location.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.mapper.WeatherDataToSnapshotMapper
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.policy.SnapshotFreshnessPolicy
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import java.time.Instant

@Factory
class RefreshLocationFavoritesWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val getWeather: GetWeather,
  private val snapshotMapper: WeatherDataToSnapshotMapper,
  private val snapshotRepository: LocationWeatherSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(forceAll: Boolean = false) {
    val now = Instant.ofEpochMilli(timeProvider.nowEpochMillis())
    val candidates = selectFavoritesToRefresh(forceAll = forceAll, now = now)
    if (candidates.isEmpty()) return
    refreshInParallel(favorites = candidates, capturedAt = now)
  }

  private suspend fun selectFavoritesToRefresh(
    forceAll: Boolean,
    now: Instant
  ): List<LocationFavorite> {
    val favorites = favoriteRepository.observeFavorites().first()
    if (forceAll) return favorites
    val snapshotsByLocation = snapshotRepository.observeSnapshots().first().associateBy { it.locationId }
    return favorites.filter { favorite ->
      val snapshot = snapshotsByLocation[favorite.location.id]
      snapshot == null || SnapshotFreshnessPolicy.isStale(updatedAt = snapshot.updatedAt, now = now)
    }
  }

  private suspend fun refreshInParallel(
    favorites: List<LocationFavorite>,
    capturedAt: Instant
  ) = coroutineScope {
    favorites
      .map { favorite ->
        async(Dispatchers.IO) { fetchAndPersistWeatherFor(favorite = favorite, capturedAt = capturedAt) }
      }
      .awaitAll()
  }

  private suspend fun fetchAndPersistWeatherFor(
    favorite: LocationFavorite,
    capturedAt: Instant
  ) {
    val weather = getWeather(favorite.location.toCoordinates()).first().getOrNull() ?: return
    val snapshot = snapshotMapper.toSnapshot(
      locationId = favorite.location.id,
      data = weather,
      capturedAt = capturedAt
    )
    snapshotRepository.save(snapshot = snapshot)
  }
}
