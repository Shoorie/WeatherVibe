package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileEditSheetUiState(
  val isVisible: Boolean,
  val username: String,
  val canSave: Boolean
)
