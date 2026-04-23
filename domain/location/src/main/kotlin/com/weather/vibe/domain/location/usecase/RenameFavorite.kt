package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.FavoriteRepository
import org.koin.core.annotation.Factory

@Factory
class RenameFavorite(private val repository: FavoriteRepository) {

  suspend operator fun invoke(id: Long, label: String?) {
    repository.renameFavorite(id = id, label = label?.takeIf { it.isNotBlank() })
  }
}
