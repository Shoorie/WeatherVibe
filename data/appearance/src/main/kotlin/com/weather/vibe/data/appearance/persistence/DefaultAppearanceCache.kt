package com.weather.vibe.data.appearance.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.data.appearance.persistence.mapper.AppearanceCacheMapper
import com.weather.vibe.domain.appearance.cache.AppearanceCache
import com.weather.vibe.domain.appearance.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [AppearanceCache::class])
internal class DefaultAppearanceCache(
  @param:AppearanceQualifier
  private val dataStore: DataStore<AppearanceCacheData>,
  private val mapper: AppearanceCacheMapper
) : AppearanceCache {

  override fun observeThemeMode(): Flow<ThemeMode> =
    dataStore.data.map { mapper.toDomain(proto = it.themeMode) }

  override suspend fun saveThemeMode(mode: ThemeMode) {
    dataStore.updateData {
      it.toBuilder()
        .setThemeMode(mapper.toProto(mode = mode))
        .build()
    }
  }
}
