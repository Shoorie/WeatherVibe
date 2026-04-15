package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.weather.model.Coordinates
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GetStartingCoordinates internal constructor(
  private val observeCurrentLocation: ObserveCurrentLocation
) {

  suspend operator fun invoke(selected: Location?): Coordinates? =
    (selected ?: observeCurrentLocation().first())?.toCoordinates()
}
