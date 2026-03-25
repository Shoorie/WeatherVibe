package com.weather.vibe.data.settings.remote.api

import com.weather.vibe.data.settings.remote.dto.SettingsResponse

interface SettingsApiService {
  suspend fun fetchSettingsItems(): List<SettingsResponse>
}

