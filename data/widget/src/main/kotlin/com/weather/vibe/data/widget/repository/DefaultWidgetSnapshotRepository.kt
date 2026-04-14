package com.weather.vibe.data.widget.repository

import androidx.datastore.core.DataStore
import com.weather.vibe.data.widget.persistence.WidgetSnapshotCacheData
import com.weather.vibe.data.widget.persistence.WidgetSnapshotQualifier
import com.weather.vibe.data.widget.persistence.mapper.WidgetSnapshotCacheMapper
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [WidgetSnapshotRepository::class])
internal class DefaultWidgetSnapshotRepository(
  @param:WidgetSnapshotQualifier
  private val dataStore: DataStore<WidgetSnapshotCacheData>,
  private val mapper: WidgetSnapshotCacheMapper
) : WidgetSnapshotRepository {

  override fun observe(locationId: Long): Flow<WidgetSnapshot?> =
    dataStore.data.map { data ->
      data.snapshotsMap[locationId]
        ?.let(mapper::toDomain)
    }

  override suspend fun get(locationId: Long): WidgetSnapshot? =
    dataStore.data.first()
      .snapshotsMap[locationId]
      ?.let(mapper::toDomain)

  override suspend fun save(snapshot: WidgetSnapshot) {
    dataStore.updateData { current ->
      current.toBuilder()
        .putSnapshots(snapshot.location.id, mapper.toEntry(snapshot))
        .build()
    }
  }
}
