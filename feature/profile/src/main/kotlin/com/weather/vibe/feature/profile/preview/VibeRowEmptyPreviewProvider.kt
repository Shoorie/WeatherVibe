package com.weather.vibe.feature.profile.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.profile.presentation.state.ProfileVibeRowUiState.Empty

internal class VibeRowEmptyPreviewProvider :
  PreviewParameterProvider<Empty> {

  private val first: Empty =
    Empty(
      ctaLabel = "Rate your first day",
      onClickLabel = "Rate your first day",
      title = "Your vibe"
    )

  override val values: Sequence<Empty> =
    sequenceOf(first)
}
