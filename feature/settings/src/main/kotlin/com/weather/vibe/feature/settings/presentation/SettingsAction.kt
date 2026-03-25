package com.weather.vibe.feature.settings.presentation

internal sealed interface SettingsAction {
  data object RefreshClick : SettingsAction
}

