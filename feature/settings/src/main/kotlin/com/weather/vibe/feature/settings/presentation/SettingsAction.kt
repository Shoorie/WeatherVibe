package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.BriefTone

internal sealed interface SettingsAction {
  data class AlertsToggle(val enabled: Boolean) : SettingsAction
  data object BackClick : SettingsAction
  data class BriefToneSelect(val tone: BriefTone) : SettingsAction
  data class GenreRemove(val genre: String) : SettingsAction
  data class MorningBriefToggle(val enabled: Boolean) : SettingsAction
  data object TemperatureUnitToggle : SettingsAction
}
