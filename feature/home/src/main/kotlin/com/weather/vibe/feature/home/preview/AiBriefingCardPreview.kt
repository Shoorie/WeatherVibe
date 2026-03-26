package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Error
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Loading

internal class AiBriefingCardPreview :
  PreviewParameterProvider<BriefingUiState> {

  override val values: Sequence<BriefingUiState> =
    sequenceOf(
      Loading,
      Loaded(
        text = "Expect a mild and partly cloudy day with " +
          "a light breeze — a good day for a " +
          "walk before the evening rain arrives."
      ),
      Error
    )
}
