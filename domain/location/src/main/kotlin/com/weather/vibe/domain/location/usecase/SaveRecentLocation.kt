package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationResult
import com.weather.vibe.domain.location.repository.LocationRepository
import org.koin.core.annotation.Factory

@Factory
class SaveRecentLocation(private val repository: LocationRepository) {

  suspend operator fun invoke(location: LocationResult) {
    repository.saveRecentLocation(location)
  }
}
