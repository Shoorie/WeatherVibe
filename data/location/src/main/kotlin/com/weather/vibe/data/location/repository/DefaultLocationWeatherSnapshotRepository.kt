package com.weather.vibe.data.location.repository

import androidx.room.withTransaction
import com.weather.vibe.data.location.local.LocationDatabase
import com.weather.vibe.data.location.local.dao.LocationWeatherSnapshotDao
import com.weather.vibe.data.location.local.mapper.LocationWeatherSnapshotCacheMapper
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationWeatherSnapshotRepository::class])
internal class DefaultLocationWeatherSnapshotRepository(
  private val database: LocationDatabase,
  private val dao: LocationWeatherSnapshotDao,
  private val mapper: LocationWeatherSnapshotCacheMapper
) : LocationWeatherSnapshotRepository {

  override fun observeWeatherSnapshots(): Flow<List<LocationWeatherSnapshot>> =
    dao.observeAll()
      .map { it.map(mapper::toDomain) }
      .flowOn(IO)

  override suspend fun findById(locationId: Long): LocationWeatherSnapshot? =
    withContext(IO) {
      dao.findById(locationId)
        ?.let(mapper::toDomain)
    }

  override suspend fun save(snapshot: LocationWeatherSnapshot) =
    database.withTransaction {

      if (!dao.favoriteExists(snapshot.locationId)) {
        return@withTransaction
      }

      dao.upsert(mapper.toEntity(snapshot))
    }

  override suspend fun remove(locationId: Long) {
    withContext(IO) { dao.deleteById(locationId) }
  }
}
