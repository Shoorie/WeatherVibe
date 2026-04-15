package com.weather.vibe.domain.widget.repository

import com.weather.vibe.domain.widget.model.WidgetSnapshot
import kotlinx.coroutines.flow.Flow

interface WidgetSnapshotRepository {
  fun observe(): Flow<WidgetSnapshot?>
  suspend fun save(snapshot: WidgetSnapshot)
}
