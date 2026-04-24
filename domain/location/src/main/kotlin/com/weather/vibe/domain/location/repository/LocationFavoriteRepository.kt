package com.weather.vibe.domain.location.repository

import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationFavorite
import kotlinx.coroutines.flow.Flow

interface LocationFavoriteRepository {

  fun observeFavorites(): Flow<List<LocationFavorite>>
  suspend fun findById(id: Long): LocationFavorite?
  suspend fun removeFavorite(id: Long)
  suspend fun renameFavorite(id: Long, label: String?)

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

  /**
   * Rewrites the `position` of each favorite so that it matches its index in [orderedIds].
   * Applied transactionally — all positions land together or none do.
   */
  suspend fun reorderFavorites(orderedIds: List<Long>)

  /**
   * Re-inserts a previously removed favorite and places it at the index [removedFavoriteId]
   * held inside [originalOrder]. The new row is assigned a fresh id, substituted into the
   * order, and the full reorder is committed inside a single transaction so the list is
   * never observable in a partial state.
   *
   * @throws LocationFavoritesLimitReached when adding would exceed [maxAllowed].
   */
  suspend fun restoreFavoriteAtOriginalPosition(
    location: Location,
    label: String?,
    removedFavoriteId: Long,
    originalOrder: List<Long>,
    maxAllowed: Int
  )
}
