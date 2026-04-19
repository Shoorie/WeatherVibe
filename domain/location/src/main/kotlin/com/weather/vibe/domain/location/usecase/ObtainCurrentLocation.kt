package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.provider.CurrentLocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

@Factory
class ObtainCurrentLocation internal constructor(
  private val provider: CurrentLocationProvider
) {

  operator fun invoke(): Flow<Result<Location>> =
    flow { emit(Result.success(provider.locate())) }
      .catch { emit(Result.failure(it)) }
      .flowOn(Dispatchers.IO)
}
