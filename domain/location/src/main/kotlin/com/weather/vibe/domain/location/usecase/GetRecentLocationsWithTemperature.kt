package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.usecase.GetCurrentTemperature
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class GetRecentLocationsWithTemperature(
  private val getCurrentTemperature: GetCurrentTemperature,
  private val getRecentLocations: GetRecentLocations
) {

  operator fun invoke(): Flow<Result<List<LocationWithTemperature>>> =
    getRecentLocations().map { result ->
      result.fold(
        onSuccess = { locations -> Result.success(enrich(locations)) },
        onFailure = { throwable -> Result.failure(throwable) }
      )
    }

  private suspend fun enrich(
    locations: List<Location>
  ): List<LocationWithTemperature> =
    coroutineScope {
      locations
        .map { location -> async { withTemperature(location) } }
        .awaitAll()
    }

  private suspend fun withTemperature(location: Location): LocationWithTemperature =
    LocationWithTemperature(
      location = location,
      currentTemperature = getCurrentTemperature(location.toCoordinates())
        .first()
        .getOrNull()
    )
}
