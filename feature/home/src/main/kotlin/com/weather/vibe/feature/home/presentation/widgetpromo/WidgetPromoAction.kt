package com.weather.vibe.feature.home.presentation.widgetpromo

internal sealed interface WidgetPromoAction {
  data class HomeReady(val widgetAlreadyPinned: Boolean) : WidgetPromoAction
  data object AddClick : WidgetPromoAction
  data object DismissClick : WidgetPromoAction
}
