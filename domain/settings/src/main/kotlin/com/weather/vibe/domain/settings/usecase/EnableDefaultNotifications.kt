package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import org.koin.core.annotation.Factory

@Factory
class EnableDefaultNotifications internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke() {
    cache.update { settings ->
      settings
        .withMoodReminderEnabled(enabled = true)
        .withMorningBriefEnabled(enabled = true)
        .withWeatherAlertsEnabled(enabled = true)
    }
  }
}
