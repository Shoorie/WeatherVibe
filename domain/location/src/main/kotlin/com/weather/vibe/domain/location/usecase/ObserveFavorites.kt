package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Favorite
import com.weather.vibe.domain.location.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveFavorites(private val repository: FavoriteRepository) {

  operator fun invoke(): Flow<Result<List<Favorite>>> =
    repository.observeFavorites()
      .map { Result.success(it) }
      .catch { emit(Result.failure(it)) }
}
