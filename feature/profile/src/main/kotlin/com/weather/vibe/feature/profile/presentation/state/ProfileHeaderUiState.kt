package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileHeaderUiState(
  val username: String,
  val greeting: String,
  val subtitle: String,
  val briefToneLabel: String
)
