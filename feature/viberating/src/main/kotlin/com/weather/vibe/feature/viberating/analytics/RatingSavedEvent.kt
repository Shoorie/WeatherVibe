package com.weather.vibe.feature.viberating.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data object RatingSavedEvent : AnalyticsEvent {

  override val name: String = "mood_logged"

  override val params: Map<String, String> = emptyMap()
}
