package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeWidgetSnapshotRepository : WidgetSnapshotRepository {

  private val snapshot = MutableStateFlow<WidgetSnapshot?>(null)
  private val history = mutableListOf<WidgetSnapshot>()

  val savedSnapshots: List<WidgetSnapshot>
    get() = history.toList()

  override fun observe(): Flow<WidgetSnapshot?> = snapshot.asStateFlow()

  override suspend fun save(snapshot: WidgetSnapshot) {
    this.snapshot.value = snapshot
    history += snapshot
  }
}
