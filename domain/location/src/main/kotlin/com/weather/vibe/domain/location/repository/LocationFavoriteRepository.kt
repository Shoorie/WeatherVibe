package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationFavorite
import kotlinx.coroutines.flow.Flow

interface LocationFavoriteRepository {
  fun observeFavorites(): Flow<List<LocationFavorite>>
  suspend fun findById(id: Long): LocationFavorite?
  suspend fun findByLocationId(locationId: Long): LocationFavorite?
  suspend fun count(): Int

  /**
   * Adds [location] to favorites when under [maxAllowed] and not already present.
   * No-op when the location is already a favorite.
   * @throws LocationFavoritesLimitReached when adding would exceed [maxAllowed].
   */
  suspend fun addFavoriteWithinLimit(
    location: Location,
    label: String?,
    maxAllowed: Int
  )

  suspend fun removeFavorite(id: Long)
  suspend fun renameFavorite(id: Long, label: String?)
}
