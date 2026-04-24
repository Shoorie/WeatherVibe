package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveLocationFavoritesCount(
  private val repository: LocationFavoriteRepository
) {

  operator fun invoke(): Flow<Result<Int>> =
    repository.observeCount()
      .map { Result.success(it) }
      .catch { emit(Result.failure(it)) }
}
