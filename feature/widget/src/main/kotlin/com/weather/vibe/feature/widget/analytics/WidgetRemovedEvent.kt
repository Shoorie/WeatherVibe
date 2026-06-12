package com.weather.vibe.feature.widget.analytics

import com.weather.vibe.core.analytics.AnalyticsEvent

internal data object WidgetRemovedEvent : AnalyticsEvent {

  override val name: String = "widget_removed"

  override val params: Map<String, String> = emptyMap()
}
