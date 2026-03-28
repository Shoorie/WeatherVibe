package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.UserSettings
import org.koin.core.annotation.Factory

@Factory
class SaveUserSettings internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke(settings: UserSettings) {
    cache.save(settings = settings)
  }
}
