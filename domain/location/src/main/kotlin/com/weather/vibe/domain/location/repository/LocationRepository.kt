package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.model.LocationResult

interface LocationRepository {
  suspend fun getRecentLocations(limit: Int): List<LocationResult>
  suspend fun saveRecentLocation(location: LocationResult)
  suspend fun searchLocations(query: String): List<LocationResult>
}
