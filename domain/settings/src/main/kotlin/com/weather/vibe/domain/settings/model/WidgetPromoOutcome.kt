package com.weather.vibe.domain.settings.model

sealed interface WidgetPromoOutcome {
  data object Skip : WidgetPromoOutcome
  data object Reveal : WidgetPromoOutcome
}
