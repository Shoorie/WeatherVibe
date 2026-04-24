package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class ObserveLocationFavoritesWithWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val snapshotRepository: LocationWeatherSnapshotRepository
) {

  operator fun invoke(): Flow<Result<List<LocationFavoriteWithWeather>>> =
    combine(
      favoriteRepository.observeFavorites(),
      snapshotRepository.observeWeatherSnapshots()
    ) { favorites, snapshots -> joinByLocation(favorites, snapshots) }
      .map { success(it) }
      .catch { emit(failure(it)) }

  private fun joinByLocation(
    favorites: List<LocationFavorite>,
    snapshots: List<LocationWeatherSnapshot>
  ): List<LocationFavoriteWithWeather> {
    val snapshotsByLocation = snapshots.associateBy { it.locationId }
    return favorites.map { favorite ->
      LocationFavoriteWithWeather(
        favorite = favorite,
        snapshot = snapshotsByLocation[favorite.location.id]
      )
    }
  }
}
