package com.weather.vibe.data.settings.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.data.settings.persistence.mapper.SettingsCacheMapper
import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [SettingsCache::class])
internal class DefaultSettingsCache(
  @param:UserSettingsQualifier
  private val dataStore: DataStore<UserSettingsCacheData>,
  private val mapper: SettingsCacheMapper
) : SettingsCache {

  override fun get(): Flow<UserSettings> =
    dataStore.data.map(mapper::toDomain)

  override suspend fun save(settings: UserSettings) {
    dataStore.updateData {
      it.toBuilder()
        .setAiPersona(settings.briefTone.name)
        .setExcludedGenres(settings.excludedGenresText)
        .setTemperatureUnit(settings.temperatureUnit.name)
        .build()
    }
  }
}
