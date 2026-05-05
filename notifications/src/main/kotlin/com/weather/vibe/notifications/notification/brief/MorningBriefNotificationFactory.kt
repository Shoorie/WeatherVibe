package com.weather.vibe.notifications.notification.brief

import com.weather.vibe.notifications.notification.AlertNotification
import com.weather.vibe.notifications.notification.NotificationChannelKind.MORNING_BRIEF
import com.weather.vibe.notifications.notification.NotificationIds
import com.weather.vibe.notifications.ui.MorningBriefResources
import org.koin.core.annotation.Factory

@Factory
class MorningBriefNotificationFactory internal constructor(
  private val resources: MorningBriefResources
) {

  fun create(briefText: String): AlertNotification =
    AlertNotification(
      body = briefText,
      id = NotificationIds.MORNING_BRIEF,
      kind = MORNING_BRIEF,
      title = resources.title()
    )
}
