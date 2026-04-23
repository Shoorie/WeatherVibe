package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.FavoriteRepository
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import org.koin.core.annotation.Factory

@Factory
class RemoveFavorite(
  private val repository: FavoriteRepository,
  private val snapshotRepository: LocationWeatherSnapshotRepository
) {

  suspend operator fun invoke(id: Long) {
    val favorite = repository.findById(id) ?: return
    repository.removeFavorite(id = id)
    snapshotRepository.remove(locationId = favorite.location.id)
  }
}
