package com.weather.vibe.data.widget.repository

import androidx.datastore.core.DataStore
import com.weather.vibe.data.widget.persistence.WidgetSnapshotCacheData
import com.weather.vibe.data.widget.persistence.WidgetSnapshotQualifier
import com.weather.vibe.data.widget.persistence.mapper.WidgetSnapshotCacheMapper
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.repository.WidgetSnapshotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [WidgetSnapshotRepository::class])
internal class DefaultWidgetSnapshotRepository(
  @param:WidgetSnapshotQualifier
  private val dataStore: DataStore<WidgetSnapshotCacheData>,
  private val mapper: WidgetSnapshotCacheMapper
) : WidgetSnapshotRepository {

  override fun observe(): Flow<WidgetSnapshot?> =
    dataStore.data.map { data ->
      if (data.hasSnapshot()) mapper.toDomain(data.snapshot) else null
    }

  override suspend fun save(snapshot: WidgetSnapshot) {
    dataStore.updateData { current ->
      current.toBuilder()
        .setSnapshot(mapper.toEntry(snapshot))
        .build()
    }
  }
}
