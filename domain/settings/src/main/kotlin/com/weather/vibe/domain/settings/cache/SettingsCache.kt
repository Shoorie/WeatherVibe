package com.weather.vibe.domain.settings.cache

import com.weather.vibe.domain.settings.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsCache {
  fun get(): Flow<UserSettings>
  suspend fun save(settings: UserSettings)
  suspend fun update(change: (UserSettings) -> UserSettings)
}
