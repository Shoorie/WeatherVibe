package com.weather.vibe.data.widget.repository

import androidx.datastore.core.DataStore
import com.weather.vibe.data.widget.persistence.PinnedWidgetCacheData
import com.weather.vibe.data.widget.persistence.PinnedWidgetQualifier
import com.weather.vibe.data.widget.persistence.mapper.WidgetLocationEntryMapper
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.repository.PinnedWidgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [PinnedWidgetRepository::class])
internal class DefaultPinnedWidgetRepository(
  @param:PinnedWidgetQualifier
  private val dataStore: DataStore<PinnedWidgetCacheData>,
  private val locationMapper: WidgetLocationEntryMapper
) : PinnedWidgetRepository {

  override fun observeAll(): Flow<Map<String, Location>> =
    dataStore.data.map { data ->
      data.widgetsMap
        .mapValues { (_, entry) -> locationMapper.toDomain(entry) }
    }

  override suspend fun get(glanceId: String): Location? =
    dataStore.data.first()
      .widgetsMap[glanceId]
      ?.let(locationMapper::toDomain)

  override suspend fun pin(glanceId: String, location: Location) {
    dataStore.updateData { current ->
      current.toBuilder()
        .putWidgets(glanceId, locationMapper.toEntry(location))
        .build()
    }
  }

  override suspend fun unpin(glanceId: String) {
    dataStore.updateData { current ->
      current.toBuilder()
        .removeWidgets(glanceId)
        .build()
    }
  }
}
