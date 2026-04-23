package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.LocationWeatherSnapshotDao
import com.weather.vibe.data.location.local.mapper.LocationWeatherSnapshotCacheMapper
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationWeatherSnapshotRepository::class])
internal class DefaultLocationWeatherSnapshotRepository(
  private val dao: LocationWeatherSnapshotDao,
  private val mapper: LocationWeatherSnapshotCacheMapper
) : LocationWeatherSnapshotRepository {

  override fun observeSnapshots(): Flow<List<LocationWeatherSnapshot>> =
    dao.observeAll()
      .map { entities -> entities.map(mapper::toDomain) }
      .flowOn(Dispatchers.IO)

  override suspend fun findById(locationId: Long): LocationWeatherSnapshot? =
    withContext(Dispatchers.IO) {
      dao.findById(locationId = locationId)?.let(mapper::toDomain)
    }

  override suspend fun save(snapshot: LocationWeatherSnapshot) {
    withContext(Dispatchers.IO) {
      dao.upsertIfFavoriteExists(entity = mapper.toEntity(snapshot = snapshot))
    }
  }

  override suspend fun remove(locationId: Long) {
    withContext(Dispatchers.IO) { dao.deleteById(locationId = locationId) }
  }
}
