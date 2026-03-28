package com.weather.vibe.feature.settings.preview

import com.weather.vibe.domain.settings.model.Persona.FORMAL
import com.weather.vibe.domain.settings.model.Persona.SARCASTIC
import com.weather.vibe.domain.settings.model.Persona.WITTY
import com.weather.vibe.feature.settings.presentation.state.PersonaOptionUiState

internal object SettingsPreviewData {

  val personaOptions: List<PersonaOptionUiState> = listOf(
    PersonaOptionUiState(
      isSelected = true,
      label = "Witty & Friendly",
      persona = WITTY
    ),
    PersonaOptionUiState(
      isSelected = false,
      label = "Formal & Professional",
      persona = FORMAL
    ),
    PersonaOptionUiState(
      isSelected = false,
      label = "Sarcastic & Edgy",
      persona = SARCASTIC
    )
  )
}
