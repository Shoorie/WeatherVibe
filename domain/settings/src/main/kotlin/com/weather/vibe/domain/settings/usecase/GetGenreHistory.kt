package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.GenreHistoryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GetGenreHistory internal constructor(
  private val cache: GenreHistoryCache
) {

  operator fun invoke(): Flow<Result<Set<String>>> =
    cache.get()
      .map { success(it) }
      .catch { emit(failure(it)) }
}
