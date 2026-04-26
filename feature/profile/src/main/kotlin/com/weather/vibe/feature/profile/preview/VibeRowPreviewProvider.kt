package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded

internal class VibeRowPreviewProvider :
  PreviewParameterProvider<ProfileVibeRowUiState> {

  private val loadedWithStreak: Loaded =
    Loaded(
      averageLabel = "4.5/5",
      onClickLabel = "Open vibe history",
      streakLabel = "2 days in a row 🔥",
      title = "Your vibe"
    )

  private val loadedNoStreak: Loaded =
    Loaded(
      averageLabel = "3.8/5",
      onClickLabel = "Open vibe history",
      streakLabel = null,
      title = "Your vibe"
    )

  private val empty: Empty =
    Empty(
      ctaLabel = "Rate your first day",
      onClickLabel = "Rate your first day",
      title = "Your vibe"
    )

  override val values: Sequence<ProfileVibeRowUiState> =
    sequenceOf(loadedWithStreak, loadedNoStreak, empty)
}
