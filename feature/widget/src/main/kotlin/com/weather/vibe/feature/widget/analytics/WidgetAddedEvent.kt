package com.weather.vibe.feature.widget.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data object WidgetAddedEvent : AnalyticsEvent {

  override val name: String = "widget_added"

  override val params: Map<String, String> = emptyMap()
}
