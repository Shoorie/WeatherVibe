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

  override fun observeCount(): Flow<Int> =
    dao.observeCount()
      .flowOn(IO)

  override suspend fun findById(id: Long): LocationFavorite? =
    withContext(IO) {
      dao.findById(id)
        ?.let(mapper::toDomain)
    }

  override suspend fun removeFavorite(id: Long) =
    withContext(IO) {
      dao.deleteByIdAndPromoteDefault(id = id)
    }

  override suspend fun renameFavorite(id: Long, label: String?) =
    withContext(IO) {
      dao.updateLabel(id = id, label = label)
    }

  override suspend fun addFavoriteWithinLimit(
    location: Location,
    label: String?,
    maxAllowed: Int
  ) = insertFavoriteWithinLimit(
    location = location,
    label = label,
    maxAllowed = maxAllowed
  )

  override suspend fun reorderFavorites(orderedIds: List<Long>) =
    database.withTransaction {
      updatePositions(orderedIds = orderedIds)
    }

  override suspend fun restoreFavoriteAtOriginalPosition(
    location: Location,
    label: String?,
    removedFavoriteId: Long,
    originalOrder: List<Long>,
    maxAllowed: Int
  ) = insertFavoriteWithinLimit(
    location = location,
    label = label,
    maxAllowed = maxAllowed
  ) { insertedId ->
    val restoredOrder = originalOrder
      .map { id -> if (id == removedFavoriteId) insertedId else id }
    updatePositions(orderedIds = restoredOrder)
  }

  private suspend fun insertFavoriteWithinLimit(
    location: Location,
    label: String?,
    maxAllowed: Int,
    onInserted: suspend (insertedId: Long) -> Unit = {}
  ) = database.withTransaction {

    if (dao.findByLocationId(location.id) != null) {
      return@withTransaction
    }

    val existingCount = dao.count()
    if (existingCount >= maxAllowed) {
      throw LocationFavoritesLimitReached(limit = maxAllowed)
    }

    val insertedId = insertFavorite(
      location = location,
      label = label,
      isDefault = existingCount == 0
    )

    onInserted(insertedId)
  }

  private suspend fun insertFavorite(
    location: Location,
    label: String?,
    isDefault: Boolean
  ): Long {
    val entity = mapper.toEntity(
      location = location,
      label = label,
      position = dao.maxPosition() + 1,
      isDefault = isDefault
    )
    return dao.insert(entity = entity)
  }

  private suspend fun updatePositions(orderedIds: List<Long>) {
    orderedIds.forEachIndexed { index, id ->
      dao.updatePosition(id = id, position = index)
    }
  }
}
