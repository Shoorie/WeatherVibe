package com.weather.vibe.feature.settings.presentation

internal sealed interface SettingsEvent {
  data object NavigateBack : SettingsEvent
  data object OpenSystemNotificationSettings : SettingsEvent
}
