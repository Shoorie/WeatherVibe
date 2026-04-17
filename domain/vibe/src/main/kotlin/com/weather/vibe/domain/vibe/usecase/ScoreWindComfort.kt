package com.weather.vibe.domain.vibe.usecase

import org.koin.core.annotation.Factory

@Factory
internal class ScoreWindComfort {

  operator fun invoke(kmh: Double): Int = when {
    kmh < LIGHT_BREEZE -> NO_PENALTY
    kmh < STIFF_BREEZE -> MILD_PENALTY
    kmh < STRONG_WIND -> MODERATE_PENALTY
    else -> HARSH_PENALTY
  }

  private companion object {
    const val LIGHT_BREEZE = 20.0
    const val STIFF_BREEZE = 40.0
    const val STRONG_WIND = 60.0
    const val NO_PENALTY = 0
    const val MILD_PENALTY = 5
    const val MODERATE_PENALTY = 10
    const val HARSH_PENALTY = 15
  }
}
