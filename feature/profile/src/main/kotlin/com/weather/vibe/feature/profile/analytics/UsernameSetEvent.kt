package com.weather.vibe.feature.profile.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data object UsernameSetEvent : AnalyticsEvent {

  override val name: String = "username_set"

  override val params: Map<String, String> = emptyMap()
}
