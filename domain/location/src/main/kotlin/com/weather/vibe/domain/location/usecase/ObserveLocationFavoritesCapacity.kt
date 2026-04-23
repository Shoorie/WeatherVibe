package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationFavoritesCapacity
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveLocationFavoritesCapacity(private val repository: LocationFavoriteRepository) {

  operator fun invoke(): Flow<LocationFavoritesCapacity> =
    repository.observeFavorites().map { favorites ->
      LocationFavoritesCapacity(
        used = favorites.size,
        max = LocationFavoritesPolicy.MAX_FAVORITES
      )
    }
}
