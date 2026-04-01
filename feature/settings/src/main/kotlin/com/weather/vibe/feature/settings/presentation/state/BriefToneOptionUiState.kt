package com.weather.vibe.feature.settings.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.settings.model.BriefTone

@Immutable
internal data class BriefToneOptionUiState(
  val description: String,
  val isSelected: Boolean,
  val label: String,
  val tone: BriefTone
)
