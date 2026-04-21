package com.weather.vibe.domain.profile.cache

import com.weather.vibe.domain.profile.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileCache {
  fun observeProfile(): Flow<Profile>
  suspend fun saveUsername(username: String)
  suspend fun saveInstalledAtMillis(millis: Long)
}
