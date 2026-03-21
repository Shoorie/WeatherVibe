package com.weather.vibe.domain.weather.repository

import com.weather.vibe.domain.weather.model.LocationResult

interface GeocodingRepository {
  suspend fun searchLocations(query: String): List<LocationResult>
}
