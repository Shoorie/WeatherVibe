package com.weather.vibe.feature.widget.config

internal sealed interface WidgetConfigAction {
  data class Initialize(val appWidgetId: Int) : WidgetConfigAction
  data class LocationSelect(val id: Long) : WidgetConfigAction
  data object Retry : WidgetConfigAction
  data object Cancel : WidgetConfigAction
}
