package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ScoreTier
import org.koin.core.annotation.Factory

@Factory
class ClassifyScore {

  operator fun invoke(score: Int): ScoreTier =
    when {
      score >= EXCELLENT_THRESHOLD -> ScoreTier.EXCELLENT
      score >= GOOD_THRESHOLD -> ScoreTier.GOOD
      score >= FAIR_THRESHOLD -> ScoreTier.FAIR
      else -> ScoreTier.POOR
    }

  private companion object {
    const val EXCELLENT_THRESHOLD = 85
    const val GOOD_THRESHOLD = 70
    const val FAIR_THRESHOLD = 50
  }
}
