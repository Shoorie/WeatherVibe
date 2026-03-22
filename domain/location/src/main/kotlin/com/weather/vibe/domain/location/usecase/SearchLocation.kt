package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationResult
import com.weather.vibe.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class SearchLocation(private val repository: LocationRepository) {

  operator fun invoke(query: String): Flow<Result<List<LocationResult>>> =
    flow {
      val result = repository.searchLocations(query)
      emit(Result.success(result))
    }
      .catch { emit(Result.failure(it)) }
}
