package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.repository.FavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveFavoritesWithWeather(
  private val favoriteRepository: FavoriteRepository,
  private val snapshotRepository: LocationWeatherSnapshotRepository
) {

  operator fun invoke(): Flow<Result<List<FavoriteWithWeather>>> =
    combine(
      favoriteRepository.observeFavorites(),
      snapshotRepository.observeSnapshots()
    ) { favorites, snapshots -> joinByLocation(favorites = favorites, snapshots = snapshots) }
      .map { Result.success(it) }
      .catch { emit(Result.failure(it)) }

  private fun joinByLocation(
    favorites: List<Favorite>,
    snapshots: List<LocationWeatherSnapshot>
  ): List<FavoriteWithWeather> {
    val snapshotsByLocation = snapshots.associateBy { it.locationId }
    return favorites.map { favorite ->
      FavoriteWithWeather(
        favorite = favorite,
        snapshot = snapshotsByLocation[favorite.location.id]
      )
    }
  }
}
