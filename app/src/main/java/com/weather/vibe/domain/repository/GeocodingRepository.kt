package com.weather.vibe.domain.repository

import com.weather.vibe.domain.model.LocationResult

interface GeocodingRepository {
    suspend fun searchLocations(query: String): Result<List<LocationResult>>
}
