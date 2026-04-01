package com.weather.vibe.data.settings.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.settings.cache.GenreHistoryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [GenreHistoryCache::class])
internal class DefaultGenreHistoryCache(
  @param:GenreHistoryQualifier
  private val dataStore: DataStore<GenreHistoryCacheData>
) : GenreHistoryCache {

  override fun get(): Flow<Set<String>> =
    dataStore.data.map { it.genresList.toSet() }

  override suspend fun addAll(genres: Set<String>) {
    dataStore.updateData { current ->

      val merged = current.genresList.toMutableSet()
        .apply { addAll(genres) }

      current.toBuilder()
        .clearGenres()
        .addAllGenres(merged.sorted())
        .build()
    }
  }
}
