package com.weather.vibe.feature.settings.personalization.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.domain.settings.model.BriefTone

@Immutable
internal data class PaywallUiState(
  val colorKey: PersonaColorKey,
  val emoji: String,
  val name: String,
  val sample: String,
  val tone: BriefTone
)
