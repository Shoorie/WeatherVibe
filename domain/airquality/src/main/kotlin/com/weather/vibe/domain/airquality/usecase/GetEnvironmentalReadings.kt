package com.weather.vibe.domain.airquality.usecase

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.weather.model.Coordinates
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import org.koin.core.annotation.Factory

@Factory
class GetEnvironmentalReadings internal constructor(
  private val getAirQuality: GetAirQuality,
  private val getPollen: GetPollen
) {

  suspend operator fun invoke(
    coordinates: Coordinates
  ): EnvironmentalReadings = supervisorScope {
    val airQuality = async { getAirQuality(coordinates).getOrNull() }
    val pollen = async { getPollen(coordinates).getOrNull() }
    EnvironmentalReadings(
      airQuality = airQuality.await(),
      pollen = pollen.await()
    )
  }
}
