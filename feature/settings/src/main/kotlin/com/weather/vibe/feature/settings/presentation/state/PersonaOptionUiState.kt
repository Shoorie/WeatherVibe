package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.settings.model.Persona

@Immutable
internal data class PersonaOptionUiState(
  val isSelected: Boolean,
  val label: String,
  val persona: Persona
)
