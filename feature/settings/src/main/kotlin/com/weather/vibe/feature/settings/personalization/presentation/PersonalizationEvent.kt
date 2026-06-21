package com.weather.vibe.feature.settings.personalization.presentation

internal sealed interface PersonalizationEvent {
  data object NavigateBack : PersonalizationEvent
  data object ShowPremiumUnavailable : PersonalizationEvent
}
