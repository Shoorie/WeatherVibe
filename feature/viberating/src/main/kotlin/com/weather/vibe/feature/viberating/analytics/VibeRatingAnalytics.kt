package com.weather.vibe.feature.viberating.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import org.koin.core.annotation.Single

@Single
internal class VibeRatingAnalytics(
  private val logger: AnalyticsLogger
) {

  fun onRatingSaved() =
    logger.log(RatingSavedEvent)
}
