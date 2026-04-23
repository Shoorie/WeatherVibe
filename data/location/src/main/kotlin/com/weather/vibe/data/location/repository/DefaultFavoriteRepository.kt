package com.weather.vibe.data.location.repository

import com.weather.vibe.data.location.local.dao.FavoriteDao
import com.weather.vibe.data.location.local.mapper.FavoriteCacheMapper
import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [FavoriteRepository::class])
internal class DefaultFavoriteRepository(
  private val dao: FavoriteDao,
  private val mapper: FavoriteCacheMapper
) : FavoriteRepository {

  override fun observeFavorites(): Flow<List<Favorite>> =
    dao.observeAll().map { entities -> entities.map(mapper::toDomain) }

  override suspend fun findById(id: Long): Favorite? =
    dao.findById(id = id)?.let(mapper::toDomain)

  override suspend fun findByLocationId(locationId: Long): Favorite? =
    dao.findByLocationId(locationId = locationId)?.let(mapper::toDomain)

  override suspend fun count(): Int = dao.count()

  override suspend fun addFavorite(location: Location, label: String?) {
    if (dao.findByLocationId(locationId = location.id) != null) return
    val nextPosition = dao.maxPosition() + 1
    val isFirst = dao.count() == 0
    val entity = mapper.toEntity(
      location = location,
      label = label,
      position = nextPosition,
      isDefault = isFirst
    )
    dao.insert(entity = entity)
  }

  override suspend fun removeFavorite(id: Long) {
    dao.deleteByIdAndPromoteDefault(id = id)
  }

  override suspend fun renameFavorite(id: Long, label: String?) {
    dao.updateLabel(id = id, label = label)
  }
}
