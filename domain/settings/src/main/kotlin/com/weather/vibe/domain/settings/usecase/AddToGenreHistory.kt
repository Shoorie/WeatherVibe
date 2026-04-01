package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.GenreHistoryCache
import org.koin.core.annotation.Factory

@Factory
class AddToGenreHistory internal constructor(
  private val cache: GenreHistoryCache
) {

  suspend operator fun invoke(genres: Set<String>) {
    cache.addAll(genres = genres)
  }
}
