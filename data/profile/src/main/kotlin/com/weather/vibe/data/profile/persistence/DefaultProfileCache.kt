package com.weather.vibe.data.profile.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.profile.cache.ProfileCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [ProfileCache::class])
internal class DefaultProfileCache(
  @param:ProfileQualifier
  private val dataStore: DataStore<ProfileCacheData>
) : ProfileCache {

  override fun observeUsername(): Flow<String> =
    dataStore.data.map { it.username }

  override suspend fun saveUsername(username: String) {
    dataStore.updateData {
      it.toBuilder()
        .setUsername(username)
        .build()
    }
  }
}
