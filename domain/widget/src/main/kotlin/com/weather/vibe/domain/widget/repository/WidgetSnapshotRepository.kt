package com.weather.vibe.domain.widget.repository

import com.weather.vibe.domain.widget.model.WidgetSnapshot
import kotlinx.coroutines.flow.Flow

interface WidgetSnapshotRepository {
  fun observe(locationId: Long): Flow<WidgetSnapshot?>
  suspend fun get(locationId: Long): WidgetSnapshot?
  suspend fun save(snapshot: WidgetSnapshot)
}
