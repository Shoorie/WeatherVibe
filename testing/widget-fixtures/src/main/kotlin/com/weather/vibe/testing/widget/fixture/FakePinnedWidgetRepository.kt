package com.weather.vibe.testing.widget.fixture

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.repository.PinnedWidgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakePinnedWidgetRepository : PinnedWidgetRepository {

  private val state = MutableStateFlow<Map<String, Location>>(emptyMap())

  val pinned: Map<String, Location>
    get() = state.value

  override fun observeAll(): Flow<Map<String, Location>> =
    state

  override suspend fun get(glanceId: String): Location? =
    state.value[glanceId]

  override suspend fun pin(glanceId: String, location: Location) {
    state.update { it + (glanceId to location) }
  }

  override suspend fun unpin(glanceId: String) {
    state.update { it - glanceId }
  }
}
