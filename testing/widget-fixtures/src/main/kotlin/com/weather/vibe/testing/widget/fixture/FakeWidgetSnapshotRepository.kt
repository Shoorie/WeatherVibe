package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeWidgetSnapshotRepository : WidgetSnapshotRepository {

  private val snapshots = MutableStateFlow<Map<Long, WidgetSnapshot>>(emptyMap())

  val savedSnapshots: List<WidgetSnapshot>
    get() = snapshots.value.values.toList()

  override fun observe(locationId: Long): Flow<WidgetSnapshot?> =
    snapshots.map { it[locationId] }

  override suspend fun get(locationId: Long): WidgetSnapshot? =
    snapshots.value[locationId]

  override suspend fun save(snapshot: WidgetSnapshot) {
    snapshots.update { it + (snapshot.location.id to snapshot) }
  }
}
