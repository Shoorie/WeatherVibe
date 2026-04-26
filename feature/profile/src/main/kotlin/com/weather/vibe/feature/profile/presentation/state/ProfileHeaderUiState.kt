package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class ProfileHeaderUiState(
  val avatarInitial: String,
  val briefToneLabel: String,
  val greeting: String,
  val showWavingHand: Boolean,
  val subtitle: String,
  val username: String
)
