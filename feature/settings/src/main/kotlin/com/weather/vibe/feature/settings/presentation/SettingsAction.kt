package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.Persona

internal sealed interface SettingsAction {
  data object BackClick : SettingsAction
  data class ExcludedGenresChange(val genres: String) : SettingsAction
  data class PersonaSelect(val persona: Persona) : SettingsAction
  data object TemperatureUnitToggle : SettingsAction
}
