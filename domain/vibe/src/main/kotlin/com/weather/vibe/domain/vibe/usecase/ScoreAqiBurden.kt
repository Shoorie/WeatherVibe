package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.airquality.model.AqiLevel
import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.FAIR
import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.MODERATE
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.VERY_POOR
import org.koin.core.annotation.Factory

@Factory
internal class ScoreAqiBurden {

  operator fun invoke(level: AqiLevel?): Int = when (level) {
    null, GOOD -> NO_PENALTY
    FAIR -> FAIR_PENALTY
    MODERATE -> MODERATE_PENALTY
    POOR -> POOR_PENALTY
    VERY_POOR -> VERY_POOR_PENALTY
    EXTREMELY_POOR -> EXTREMELY_POOR_PENALTY
  }

  private companion object {
    const val NO_PENALTY = 0
    const val FAIR_PENALTY = 5
    const val MODERATE_PENALTY = 15
    const val POOR_PENALTY = 25
    const val VERY_POOR_PENALTY = 35
    const val EXTREMELY_POOR_PENALTY = 40
  }
}
