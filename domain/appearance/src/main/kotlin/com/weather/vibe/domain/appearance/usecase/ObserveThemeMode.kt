package com.weather.vibe.domain.appearance.usecase

import com.weather.vibe.domain.appearance.cache.AppearanceCache
import com.weather.vibe.domain.appearance.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveThemeMode internal constructor(
  private val cache: AppearanceCache
) {

  operator fun invoke(): Flow<ThemeMode> =
    cache.observeThemeMode()
}
