package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveCurrentLocation internal constructor(
  private val getRecentLocations: GetRecentLocations
) {

  operator fun invoke(): Flow<Location?> =
    getRecentLocations().map { result ->
      result.getOrNull()?.firstOrNull()
    }
}
