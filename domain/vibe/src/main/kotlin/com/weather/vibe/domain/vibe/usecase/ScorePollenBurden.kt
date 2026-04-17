package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.MODERATE
import com.weather.vibe.domain.airquality.model.PollenLevel.VERY_HIGH
import org.koin.core.annotation.Factory

@Factory
internal class ScorePollenBurden {

  operator fun invoke(pollen: Pollen?): Int {

    val highestLevel = pollen?.readings
      ?.maxOfOrNull { it.level.ordinal }
      ?: return NO_PENALTY

    return when {
      highestLevel >= VERY_HIGH.ordinal -> VERY_HIGH_PENALTY
      highestLevel >= HIGH.ordinal -> HIGH_PENALTY
      highestLevel >= MODERATE.ordinal -> MODERATE_PENALTY
      else -> NO_PENALTY
    }
  }

  private companion object {
    const val NO_PENALTY = 0
    const val MODERATE_PENALTY = 5
    const val HIGH_PENALTY = 10
    const val VERY_HIGH_PENALTY = 15
  }
}
