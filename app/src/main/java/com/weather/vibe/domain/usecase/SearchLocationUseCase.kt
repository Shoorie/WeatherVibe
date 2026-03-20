package com.weather.vibe.domain.usecase

import com.weather.vibe.domain.model.LocationResult
import com.weather.vibe.domain.repository.GeocodingRepository

class SearchLocationUseCase(private val repository: GeocodingRepository) {

    suspend operator fun invoke(query: String): Result<List<LocationResult>> =
        repository.searchLocations(query)
}
