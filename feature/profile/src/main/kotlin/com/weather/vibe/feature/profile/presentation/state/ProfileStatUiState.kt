package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileStatUiState(
  val type: ProfileStatType,
  val label: String,
  val value: String,
  val onClickLabel: String
)
