package com.weather.vibe.domain.settings.cache

import kotlinx.coroutines.flow.Flow

interface GenreHistoryCache {
  fun get(): Flow<Set<String>>
  suspend fun addAll(genres: Set<String>)
}
