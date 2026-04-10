package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.model.Location

interface LocationRepository {
  suspend fun getRecentLocations(limit: Int): List<Location>
  suspend fun saveRecentLocation(location: Location)
  suspend fun searchLocations(query: String): List<Location>
}
