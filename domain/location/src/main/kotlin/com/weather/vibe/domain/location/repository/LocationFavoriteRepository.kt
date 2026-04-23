package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.model.AddLocationFavoriteOutcome
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationFavoriteRepository {
  fun observeFavorites(): Flow<List<LocationFavorite>>
  suspend fun findById(id: Long): LocationFavorite?
  suspend fun findByLocationId(locationId: Long): LocationFavorite?
  suspend fun count(): Int
  suspend fun tryAddFavorite(
    location: Location,
    label: String?,
    maxAllowed: Int
  ): AddLocationFavoriteOutcome
  suspend fun removeFavorite(id: Long)
  suspend fun renameFavorite(id: Long, label: String?)
}
