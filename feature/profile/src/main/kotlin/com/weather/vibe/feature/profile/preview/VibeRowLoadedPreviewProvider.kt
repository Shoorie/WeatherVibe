package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Loaded

internal class VibeRowLoadedPreviewProvider :
  PreviewParameterProvider<Loaded> {

  private val withStreak: Loaded =
    Loaded(
      averageLabel = "4.5/5",
      onClickLabel = "Open vibe history",
      streakLabel = "2 days in a row 🔥",
      title = "Your vibe"
    )

  private val noStreak: Loaded =
    Loaded(
      averageLabel = "3.8/5",
      onClickLabel = "Open vibe history",
      streakLabel = null,
      title = "Your vibe"
    )

  override val values: Sequence<Loaded> =
    sequenceOf(withStreak, noStreak)
}
