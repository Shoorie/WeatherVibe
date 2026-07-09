package com.weather.vibe.feature.settings.personalization.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.domain.settings.model.BriefTone

@Immutable
internal data class PersonaUiState(
  val colorKey: PersonaColorKey,
  val isLocked: Boolean,
  val isPremium: Boolean,
  val isSelected: Boolean,
  val label: String,
  val tone: BriefTone
)
