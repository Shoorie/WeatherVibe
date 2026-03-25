package com.weather.vibe.data.settings.repository

import com.weather.vibe.data.settings.mapper.toDomain
import com.weather.vibe.data.settings.remote.api.SettingsApiService
import com.weather.vibe.domain.settings.model.SettingsItem
import com.weather.vibe.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [SettingsRepository::class])
internal class DefaultSettingsRepository(
  private val apiService: SettingsApiService
) : SettingsRepository {

  override suspend fun fetchSettingsItems(): List<SettingsItem> =
    withContext(Dispatchers.IO) {
      apiService.fetchSettingsItems().map { it.toDomain() }
    }
}

