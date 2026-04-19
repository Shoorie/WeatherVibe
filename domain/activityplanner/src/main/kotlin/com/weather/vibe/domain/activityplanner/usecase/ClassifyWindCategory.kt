package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.WindCategory
import org.koin.core.annotation.Factory

@Factory
class ClassifyWindCategory {

  operator fun invoke(kmh: Double): WindCategory =
    when {
      kmh < CALM_UPPER -> WindCategory.CALM
      kmh < BREEZY_UPPER -> WindCategory.BREEZY
      kmh < WINDY_UPPER -> WindCategory.WINDY
      else -> WindCategory.GUSTY
    }

  private companion object {
    const val CALM_UPPER = 12.0
    const val BREEZY_UPPER = 22.0
    const val WINDY_UPPER = 32.0
  }
}
