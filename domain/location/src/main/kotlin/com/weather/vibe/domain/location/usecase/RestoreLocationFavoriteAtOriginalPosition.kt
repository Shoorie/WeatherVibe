package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

/**
 * Restores a removed favorite to the slot it held before removal and re-attaches its
 * weather snapshot when one was captured. The favorite insert and reorder are atomic; the
 * snapshot restore is a separate best-effort step — a failed snapshot save still leaves
 * the card in the right place and the next stale-refresh will repopulate it.
 */
@Factory
class RestoreLocationFavoriteAtOriginalPosition(
  private val favoriteRepository: LocationFavoriteRepository,
  private val restoreSnapshot: RestoreLocationWeatherSnapshot
) {

  suspend operator fun invoke(
    location: Location,
    label: String?,
    snapshot: LocationWeatherSnapshot?,
    removedFavoriteId: Long,
    originalOrder: List<Long>
  ) {
    favoriteRepository.restoreFavoriteAtOriginalPosition(
      location = location,
      label = label,
      removedFavoriteId = removedFavoriteId,
      originalOrder = originalOrder,
      maxAllowed = MAX_FAVORITES
    )
    snapshot?.let { restoreSnapshot(snapshot = it) }
  }
}
