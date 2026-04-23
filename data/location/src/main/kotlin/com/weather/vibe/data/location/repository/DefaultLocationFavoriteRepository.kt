package com.weather.vibe.data.location.repository

import androidx.room.withTransaction
import com.weather.vibe.data.location.local.LocationDatabase
import com.weather.vibe.data.location.local.dao.LocationFavoriteDao
import com.weather.vibe.data.location.local.mapper.LocationFavoriteCacheMapper
import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationFavoriteRepository::class])
internal class DefaultLocationFavoriteRepository(
  private val database: LocationDatabase,
  private val dao: LocationFavoriteDao,
  private val mapper: LocationFavoriteCacheMapper
) : LocationFavoriteRepository {

  override fun observeFavorites(): Flow<List<LocationFavorite>> =
    dao.observeAll()
      .map { it.map(mapper::toDomain) }
      .flowOn(IO)

  override suspend fun findById(id: Long): LocationFavorite? =
    withContext(IO) {
      dao.findById(id)
        ?.let(mapper::toDomain)
    }

  override suspend fun findByLocationId(locationId: Long): LocationFavorite? =
    withContext(IO) {
      dao.findByLocationId(locationId)
        ?.let(mapper::toDomain)
    }

  override suspend fun count(): Int =
    withContext(IO) { dao.count() }

  override suspend fun addFavoriteWithinLimit(
    location: Location,
    label: String?,
    maxAllowed: Int
  ) = database.withTransaction {

    if (dao.findByLocationId(location.id) != null) {
      return@withTransaction
    }

    val existingCount = dao.count()
    if (existingCount >= maxAllowed) {
      throw LocationFavoritesLimitReached(limit = maxAllowed)
    }

    val entity = mapper.toEntity(
      location = location,
      label = label,
      position = dao.maxPosition() + 1,
      isDefault = existingCount == 0
    )
    dao.insert(entity = entity)
  }

  override suspend fun removeFavorite(id: Long) {
    withContext(IO) { dao.deleteByIdAndPromoteDefault(id = id) }
  }

  override suspend fun renameFavorite(id: Long, label: String?) {
    withContext(IO) { dao.updateLabel(id = id, label = label) }
  }
}
