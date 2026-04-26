package com.weather.vibe.domain.appearance.cache

import com.weather.vibe.domain.appearance.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface AppearanceCache {
  fun observeThemeMode(): Flow<ThemeMode>
  suspend fun saveThemeMode(mode: ThemeMode)
}
