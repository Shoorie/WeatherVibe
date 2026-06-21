package com.weather.vibe.feature.settings.personalization.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey

@Immutable
internal data class NarratorUiState(
  val colorKey: PersonaColorKey,
  val emoji: String,
  val isPremium: Boolean,
  val name: String,
  val sample: String,
  val subtitle: String
)
