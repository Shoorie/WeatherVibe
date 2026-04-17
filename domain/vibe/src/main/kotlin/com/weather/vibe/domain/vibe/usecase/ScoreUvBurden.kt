package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.weather.model.UvLevel
import com.weather.vibe.domain.weather.model.UvLevel.EXTREME
import com.weather.vibe.domain.weather.model.UvLevel.HIGH
import com.weather.vibe.domain.weather.model.UvLevel.LOW
import com.weather.vibe.domain.weather.model.UvLevel.MODERATE
import com.weather.vibe.domain.weather.model.UvLevel.VERY_HIGH
import org.koin.core.annotation.Factory

@Factory
internal class ScoreUvBurden {

  operator fun invoke(uvIndex: Double): Int = when (UvLevel.from(uvIndex)) {
    LOW, MODERATE -> NO_PENALTY
    HIGH -> MILD_PENALTY
    VERY_HIGH -> MODERATE_PENALTY
    EXTREME -> HARSH_PENALTY
  }

  private companion object {
    const val NO_PENALTY = 0
    const val MILD_PENALTY = 5
    const val MODERATE_PENALTY = 10
    const val HARSH_PENALTY = 15
  }
}
