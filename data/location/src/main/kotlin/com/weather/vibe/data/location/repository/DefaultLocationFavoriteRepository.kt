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
    dao.findById(id)?.let(mapper::toDomain)

  override suspend fun findByLocationId(locationId: Long): LocationFavorite? =
    dao.findByLocationId(locationId)?.let(mapper::toDomain)

  override suspend fun count(): Int = dao.count()

  override suspend fun addFavoriteWithinLimit(
    location: Location,
    label: String?,
    maxAllowed: Int
  ) = database.withTransaction {
    if (dao.findByLocationId(location.id) != null) return@withTransaction
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
    dao.deleteByIdAndPromoteDefault(id = id)
  }

  override suspend fun renameFavorite(id: Long, label: String?) {
    dao.updateLabel(id = id, label = label)
  }

  override suspend fun reorderFavorites(orderedIds: List<Long>) = database.withTransaction {
    writeOrder(orderedIds = orderedIds)
  }

  override suspend fun restoreFavoriteAtOriginalPosition(
    location: Location,
    label: String?,
    removedFavoriteId: Long,
    originalOrder: List<Long>,
    maxAllowed: Int
  ) = database.withTransaction {
    if (dao.findByLocationId(location.id) != null) return@withTransaction
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
    val insertedId = dao.insert(entity = entity)
    val restoredOrder = originalOrder.map { id ->
      if (id == removedFavoriteId) insertedId else id
    }
    writeOrder(orderedIds = restoredOrder)
  }

  private suspend fun writeOrder(orderedIds: List<Long>) {
    orderedIds.forEachIndexed { index, id ->
      dao.updatePosition(id = id, position = index)
    }
  }
}
