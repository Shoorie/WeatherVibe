package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.policy.LocationWeatherFreshnessPolicy
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class RefreshOutdatedLocationFavoritesWeather(
  private val favoriteRepository: LocationFavoriteRepository,
  private val snapshotRepository: LocationWeatherSnapshotRepository,
  private val refreshLocationsWeather: RefreshLocationsWeather,
  private val freshnessPolicy: LocationWeatherFreshnessPolicy
) {

  suspend operator fun invoke() {

    val snapshotByLocationId = snapshotRepository
      .observeWeatherSnapshots().first()
      .associateBy { it.locationId }

    val outdatedFavorites = favoriteRepository
      .observeFavorites().first()
      .filter { freshnessPolicy.needsRefresh(snapshotByLocationId[it.location.id]) }

    refreshLocationsWeather(favorites = outdatedFavorites)
  }
}
