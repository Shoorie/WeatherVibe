package com.weather.vibe.domain.profile.cache

import kotlinx.coroutines.flow.Flow

interface ProfileCache {
  fun observeUsername(): Flow<String>
  suspend fun saveUsername(username: String)
}
