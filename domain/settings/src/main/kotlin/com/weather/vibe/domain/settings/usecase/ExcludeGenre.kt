package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import org.koin.core.annotation.Factory

@Factory
class ExcludeGenre internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke(genre: String) {
    cache.update { it.withExcludedGenre(genre) }
  }
}
