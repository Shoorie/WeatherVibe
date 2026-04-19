package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.UvCategory
import org.koin.core.annotation.Factory

@Factory
class ClassifyUvCategory {

  operator fun invoke(uvIndex: Double): UvCategory =
    when {
      uvIndex < LOW_UPPER -> UvCategory.LOW
      uvIndex < MODERATE_UPPER -> UvCategory.MODERATE
      uvIndex < HIGH_UPPER -> UvCategory.HIGH
      else -> UvCategory.VERY_HIGH
    }

  private companion object {
    const val LOW_UPPER = 3.0
    const val MODERATE_UPPER = 6.0
    const val HIGH_UPPER = 8.0
  }
}
