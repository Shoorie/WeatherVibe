package com.weather.vibe.feature.widget.config

internal sealed interface WidgetConfigEvent {
  data class Finish(val appWidgetId: Int) : WidgetConfigEvent
  data object Cancel : WidgetConfigEvent
}
