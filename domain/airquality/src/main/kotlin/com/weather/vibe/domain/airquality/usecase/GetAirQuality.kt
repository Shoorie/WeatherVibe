package com.weather.vibe.domain.airquality.usecase

import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.airquality.repository.AirQualityRepository
import com.weather.vibe.domain.weather.model.Coordinates
import org.koin.core.annotation.Factory

@Factory
class GetAirQuality internal constructor(
  private val repository: AirQualityRepository
) {

  suspend operator fun invoke(coordinates: Coordinates): AirQuality =
    repository.getAirQuality(coordinates)
}
