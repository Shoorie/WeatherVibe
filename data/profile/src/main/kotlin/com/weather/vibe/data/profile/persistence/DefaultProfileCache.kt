package com.weather.vibe.data.profile.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.data.profile.persistence.mapper.ProfileCacheMapper
import com.weather.vibe.domain.profile.cache.ProfileCache
import com.weather.vibe.domain.profile.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [ProfileCache::class])
internal class DefaultProfileCache(
  @param:ProfileQualifier
  private val dataStore: DataStore<ProfileCacheData>,
  private val mapper: ProfileCacheMapper
) : ProfileCache {

  override fun observeProfile(): Flow<Profile> =
    dataStore.data.map(mapper::toDomain)

  override suspend fun saveUsername(username: String) {
    dataStore.updateData {
      it.toBuilder()
        .setUsername(username)
        .build()
    }
  }

  override suspend fun saveInstalledAtMillis(millis: Long) {
    dataStore.updateData {
      it.toBuilder()
        .setInstalledAtMillis(millis)
        .build()
    }
  }
}
