package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class ReorderLocationFavorites(
  private val favoriteRepository: LocationFavoriteRepository
) {

  suspend operator fun invoke(orderedIds: List<Long>) {
    favoriteRepository.reorderFavorites(orderedIds = orderedIds)
  }
}
