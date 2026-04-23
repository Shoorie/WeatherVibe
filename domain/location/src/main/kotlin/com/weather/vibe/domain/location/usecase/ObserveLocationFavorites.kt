package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.repository.LocationFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveLocationFavorites(private val repository: LocationFavoriteRepository) {

  operator fun invoke(): Flow<Result<List<LocationFavorite>>> =
    repository.observeFavorites()
      .map { Result.success(it) }
      .catch { emit(Result.failure(it)) }
}
