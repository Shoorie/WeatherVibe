package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class GetRecentLocations(private val repository: LocationRepository) {

  operator fun invoke(): Flow<Result<List<Location>>> =
    repository.observeRecentLocations(MAX_RECENT)
      .map { Result.success(it) }
      .catch { emit(Result.failure(it)) }

  private companion object {
    const val MAX_RECENT = 5
  }
}
