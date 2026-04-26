package com.weather.vibe.feature.viberating.presentation.history

internal sealed interface VibeHistoryEvent {
  data object NavigateBack : VibeHistoryEvent
}
