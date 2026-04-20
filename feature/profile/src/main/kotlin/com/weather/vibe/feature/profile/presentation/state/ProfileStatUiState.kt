package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileStatUiState(
  val id: String,
  val label: String,
  val value: String
)
