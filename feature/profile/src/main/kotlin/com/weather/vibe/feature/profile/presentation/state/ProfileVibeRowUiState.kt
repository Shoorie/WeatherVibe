package com.weather.vibe.feature.profile.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface ProfileVibeRowUiState {

  val title: String
  val onClickLabel: String

  @Immutable
  data class Loaded(
    override val title: String,
    override val onClickLabel: String,
    val averageLabel: String,
    val streakLabel: String?
  ) : ProfileVibeRowUiState

  @Immutable
  data class Empty(
    override val title: String,
    override val onClickLabel: String,
    val ctaLabel: String
  ) : ProfileVibeRowUiState
}
