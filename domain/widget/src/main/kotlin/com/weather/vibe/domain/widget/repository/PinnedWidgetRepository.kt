package com.weather.vibe.domain.widget.repository

import com.weather.vibe.domain.location.model.Location
import kotlinx.coroutines.flow.Flow

interface PinnedWidgetRepository {
  fun observeAll(): Flow<Map<String, Location>>
  suspend fun get(glanceId: String): Location?
  suspend fun pin(glanceId: String, location: Location)
  suspend fun unpin(glanceId: String)
}
