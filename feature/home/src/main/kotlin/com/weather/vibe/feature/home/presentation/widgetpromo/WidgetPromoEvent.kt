package com.weather.vibe.feature.home.presentation.widgetpromo

internal sealed interface WidgetPromoEvent {
  data object RequestPin : WidgetPromoEvent
}
