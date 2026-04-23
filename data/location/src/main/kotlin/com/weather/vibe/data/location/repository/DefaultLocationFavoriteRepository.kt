package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.LocationFavoriteDao
import com.weather.vibe.data.location.local.dao.LocationFavoriteInsertOutcome
import com.weather.vibe.data.location.local.mapper.LocationFavoriteCacheMapper
import com.weather.vibe.domain.location.model.AddLocationFavoriteOutcome
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [LocationFavoriteRepository::class])
internal class DefaultLocationFavoriteRepository(
  private val dao: LocationFavoriteDao,
  private val mapper: LocationFavoriteCacheMapper
) : LocationFavoriteRepository {

  override fun observeFavorites(): Flow<List<LocationFavorite>> =
    dao.observeAll()
      .map { entities -> entities.map(mapper::toDomain) }
      .flowOn(Dispatchers.IO)

  override suspend fun findById(id: Long): LocationFavorite? =
    withContext(Dispatchers.IO) {
      dao.findById(id = id)?.let(mapper::toDomain)
    }

  override suspend fun findByLocationId(locationId: Long): LocationFavorite? =
    withContext(Dispatchers.IO) {
      dao.findByLocationId(locationId = locationId)?.let(mapper::toDomain)
    }

  override suspend fun count(): Int =
    withContext(Dispatchers.IO) { dao.count() }

  override suspend fun tryAddFavorite(
    location: Location,
    label: String?,
    maxAllowed: Int
  ): AddLocationFavoriteOutcome = withContext(Dispatchers.IO) {
    val draft = mapper.toEntity(
      location = location,
      label = label,
      position = 0,
      isDefault = false
    )
    dao.insertIfAbsentWithinLimit(entity = draft, maxAllowed = maxAllowed).toDomain()
  }

  override suspend fun removeFavorite(id: Long) {
    withContext(Dispatchers.IO) { dao.deleteByIdAndPromoteDefault(id = id) }
  }

  override suspend fun renameFavorite(id: Long, label: String?) {
    withContext(Dispatchers.IO) { dao.updateLabel(id = id, label = label) }
  }

  private fun LocationFavoriteInsertOutcome.toDomain(): AddLocationFavoriteOutcome =
    when (this) {
      LocationFavoriteInsertOutcome.Inserted -> AddLocationFavoriteOutcome.Added
      LocationFavoriteInsertOutcome.AlreadyExists -> AddLocationFavoriteOutcome.AlreadyExists
      LocationFavoriteInsertOutcome.LimitReached -> AddLocationFavoriteOutcome.LimitReached
    }
}
