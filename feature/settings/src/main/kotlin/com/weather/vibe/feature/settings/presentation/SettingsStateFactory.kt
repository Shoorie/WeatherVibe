package com.weather.vibe.feature.settings.presentation

import com.weather.vibe.domain.settings.model.Persona
import com.weather.vibe.domain.settings.model.Persona.FORMAL
import com.weather.vibe.domain.settings.model.Persona.SARCASTIC
import com.weather.vibe.domain.settings.model.Persona.WITTY
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.feature.settings.presentation.state.PersonaOptionUiState
import com.weather.vibe.feature.settings.presentation.state.SettingsUiState
import com.weather.vibe.feature.settings.ui.SettingsResources
import org.koin.core.annotation.Factory

@Factory
internal class SettingsStateFactory(
  private val resources: SettingsResources
) {

  fun create(settings: UserSettings): SettingsUiState.Loaded =
    SettingsUiState.Loaded(
      excludedGenres = settings.excludedGenres,
      isCelsius = settings.temperatureUnit == CELSIUS,
      personaOptions = createPersonaOptions(selected = settings.persona)
    )

  private fun createPersonaOptions(selected: Persona): List<PersonaOptionUiState> =
    listOf(
      PersonaOptionUiState(
        isSelected = selected == WITTY,
        persona = WITTY,
        label = resources.personaWittyLabel()
      ),
      PersonaOptionUiState(
        isSelected = selected == FORMAL,
        persona = FORMAL,
        label = resources.personaFormalLabel()
      ),
      PersonaOptionUiState(
        isSelected = selected == SARCASTIC,
        persona = SARCASTIC,
        label = resources.personaSarcasticLabel()
      )
    )
}
