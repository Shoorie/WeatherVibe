package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class RenameLocationFavorite(
  private val repository: LocationFavoriteRepository
) {

  suspend operator fun invoke(id: Long, label: String?) {
    repository.renameFavorite(
      id = id,
      label = label?.takeIf { it.isNotBlank() }
    )
  }
}
