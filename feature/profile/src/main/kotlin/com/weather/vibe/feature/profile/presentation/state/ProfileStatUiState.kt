package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileStatUiState(
  val emoji: String,
  val label: String,
  val onClickLabel: String,
  val type: ProfileStatType,
  val value: String
)
