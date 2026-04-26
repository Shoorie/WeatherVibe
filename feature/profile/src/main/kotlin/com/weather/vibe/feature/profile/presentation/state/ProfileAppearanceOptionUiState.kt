package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.domain.appearance.model.ThemeMode

@Immutable
internal data class ProfileAppearanceOptionUiState(
  val isSelected: Boolean,
  val label: String,
  val mode: ThemeMode
)
