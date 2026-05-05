package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import org.koin.core.annotation.Factory

@Factory
class DisableAllNotifications internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke() {
    cache.update { settings ->
      settings
        .withMoodReminderEnabled(enabled = false)
        .withMorningBriefEnabled(enabled = false)
        .withPollenAlertsEnabled(enabled = false)
        .withWeatherAlertsEnabled(enabled = false)
    }
  }
}
