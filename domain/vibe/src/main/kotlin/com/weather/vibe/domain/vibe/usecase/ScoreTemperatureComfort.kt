package com.weather.vibe.domain.vibe.usecase

import org.koin.core.annotation.Factory
import kotlin.math.abs

@Factory
internal class ScoreTemperatureComfort {

  operator fun invoke(apparent: Double): Int {

    val delta = abs(apparent - IDEAL_TEMPERATURE).toInt()

    return when {
      delta <= COMFORT_BAND -> NO_PENALTY
      delta <= MILD_BAND -> MILD_PENALTY
      delta <= MODERATE_BAND -> MODERATE_PENALTY
      delta <= HARSH_BAND -> HARSH_PENALTY
      else -> EXTREME_PENALTY
    }
  }

  private companion object {
    const val IDEAL_TEMPERATURE = 20.0
    const val COMFORT_BAND = 3
    const val MILD_BAND = 8
    const val MODERATE_BAND = 13
    const val HARSH_BAND = 18
    const val NO_PENALTY = 0
    const val MILD_PENALTY = 5
    const val MODERATE_PENALTY = 15
    const val HARSH_PENALTY = 25
    const val EXTREME_PENALTY = 35
  }
}
