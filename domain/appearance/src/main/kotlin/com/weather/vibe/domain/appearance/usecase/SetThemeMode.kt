package com.weather.vibe.domain.appearance.usecase

import com.weather.vibe.domain.appearance.cache.AppearanceCache
import com.weather.vibe.domain.appearance.model.ThemeMode
import org.koin.core.annotation.Factory

@Factory
class SetThemeMode internal constructor(
  private val cache: AppearanceCache
) {

  suspend operator fun invoke(mode: ThemeMode) {
    cache.saveThemeMode(mode)
  }
}
