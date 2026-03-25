package com.weather.vibe.domain.settings.repository

import com.weather.vibe.domain.settings.model.SettingsItem

interface SettingsRepository {
  suspend fun fetchSettingsItems(): List<SettingsItem>
}

