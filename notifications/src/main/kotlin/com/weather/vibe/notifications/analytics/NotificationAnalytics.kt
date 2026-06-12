package com.weather.vibe.notifications.analytics

import com.weather.vibe.core.analytics.AnalyticsLogger
import org.koin.core.annotation.Single

@Single
internal class NotificationAnalytics(
  private val logger: AnalyticsLogger
) {

  fun onNotificationShown(kind: String) =
    logger.log(NotificationShownEvent(kind = kind))
}
