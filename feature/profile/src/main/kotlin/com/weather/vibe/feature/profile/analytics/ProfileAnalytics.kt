package com.weather.vibe.feature.profile.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import org.koin.core.annotation.Single

@Single
internal class ProfileAnalytics(
  private val logger: AnalyticsLogger
) {

  fun onUsernameSaved() {
    logger.log(UsernameSetEvent)
    logger.setUserProperty(HasUsernameProperty(hasUsername = true))
  }
}
