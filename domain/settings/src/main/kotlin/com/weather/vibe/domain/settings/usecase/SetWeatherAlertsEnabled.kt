package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import org.koin.core.annotation.Factory

@Factory
class SetWeatherAlertsEnabled internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke(enabled: Boolean) {
    cache.update { it.withAlertsEnabled(enabled) }
  }
}
