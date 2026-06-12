package com.weather.vibe.notifications.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data class NotificationShownEvent(
  private val kind: String
) : AnalyticsEvent {

  override val name: String = "notification_shown"

  override val params: Map<String, String> =
    mapOf("kind" to kind)
}
