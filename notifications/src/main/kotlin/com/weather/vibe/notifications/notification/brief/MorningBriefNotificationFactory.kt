package com.weather.vibe.notifications.notification.brief

import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationIds.MORNING_BRIEF
import com.weather.vibe.notifications.ui.MorningBriefResources
import org.koin.core.annotation.Factory

@Factory
class MorningBriefNotificationFactory internal constructor(
  private val resources: MorningBriefResources
) {

  fun create(briefText: String): AlertNotification =
    AlertNotification(
      id = MORNING_BRIEF,
      title = resources.title(),
      body = briefText
    )
}
