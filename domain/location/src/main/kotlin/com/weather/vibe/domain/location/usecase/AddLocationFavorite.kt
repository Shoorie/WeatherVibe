package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class AddLocationFavorite(
  private val repository: LocationFavoriteRepository
) {

  suspend operator fun invoke(location: Location, label: String? = null) {
    repository.addFavoriteWithinLimit(
      location = location,
      label = label?.trim()?.takeIf(String::isNotEmpty),
      maxAllowed = MAX_FAVORITES
    )
  }
}
