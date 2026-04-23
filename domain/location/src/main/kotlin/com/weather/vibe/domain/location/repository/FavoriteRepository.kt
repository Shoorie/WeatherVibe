package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.model.Location
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
  fun observeFavorites(): Flow<List<Favorite>>
  suspend fun findById(id: Long): Favorite?
  suspend fun findByLocationId(locationId: Long): Favorite?
  suspend fun count(): Int
  suspend fun addFavorite(location: Location, label: String?)
  suspend fun removeFavorite(id: Long)
  suspend fun renameFavorite(id: Long, label: String?)
}
