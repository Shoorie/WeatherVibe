package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.LocationRepository
import org.koin.core.annotation.Factory

@Factory
class GetLocationById(private val repository: LocationRepository) {

  suspend operator fun invoke(id: Long): Location? =
    repository.findById(id)
}
