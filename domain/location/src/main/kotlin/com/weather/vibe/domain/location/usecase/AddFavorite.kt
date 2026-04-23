package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.error.FavoritesLimitReached
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.FavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class AddFavorite(private val repository: FavoriteRepository) {

  suspend operator fun invoke(location: Location, label: String? = null) {

    if (isAlreadyFavorite(location = location)) return
    ensureCapacityAvailable()

    repository.addFavorite(
      location = location,
      label = label.nonBlankOrNull()
    )
  }

  private suspend fun isAlreadyFavorite(location: Location): Boolean =
    repository.findByLocationId(location.id) != null

  private suspend fun ensureCapacityAvailable() {
    if (repository.count() >= FAVORITES_LIMIT) {
      throw FavoritesLimitReached(limit = FAVORITES_LIMIT)
    }
  }

  private fun String?.nonBlankOrNull(): String? =
    this?.takeIf(String::isNotBlank)

  companion object {
    const val FAVORITES_LIMIT: Int = 6
  }
}
