package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.TemperatureComfort
import org.koin.core.annotation.Factory

@Factory
class ClassifyTemperatureComfort {

  operator fun invoke(celsius: Double): TemperatureComfort =
    when {
      celsius < COLD_UPPER -> TemperatureComfort.COLD
      celsius < CHILLY_UPPER -> TemperatureComfort.CHILLY
      celsius < COMFY_UPPER -> TemperatureComfort.COMFY
      celsius < WARM_UPPER -> TemperatureComfort.WARM
      else -> TemperatureComfort.HOT
    }

  private companion object {
    const val COLD_UPPER = 5.0
    const val CHILLY_UPPER = 12.0
    const val COMFY_UPPER = 22.0
    const val WARM_UPPER = 28.0
  }
}
