package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import kotlinx.coroutines.flow.Flow

interface LocationWeatherSnapshotRepository {
  fun observeSnapshots(): Flow<List<LocationWeatherSnapshot>>
  suspend fun findById(locationId: Long): LocationWeatherSnapshot?
  suspend fun save(snapshot: LocationWeatherSnapshot)
  suspend fun remove(locationId: Long)
}
