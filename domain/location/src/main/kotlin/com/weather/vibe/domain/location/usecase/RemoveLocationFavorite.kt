package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class RemoveLocationFavorite(
  private val repository: LocationFavoriteRepository
) {

  suspend operator fun invoke(id: Long) {
    repository.removeFavorite(id = id)
  }
}
