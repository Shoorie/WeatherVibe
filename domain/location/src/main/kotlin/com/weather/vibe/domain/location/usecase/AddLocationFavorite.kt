package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.AddLocationFavoriteOutcome
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class AddLocationFavorite(private val repository: LocationFavoriteRepository) {

  suspend operator fun invoke(location: Location, label: String? = null) {
    val outcome = repository.tryAddFavorite(
      location = location,
      label = label?.takeIf(String::isNotBlank),
      maxAllowed = LocationFavoritesPolicy.MAX_FAVORITES
    )
    if (outcome is AddLocationFavoriteOutcome.LimitReached) {
      throw LocationFavoritesLimitReached(limit = LocationFavoritesPolicy.MAX_FAVORITES)
    }
  }
}
