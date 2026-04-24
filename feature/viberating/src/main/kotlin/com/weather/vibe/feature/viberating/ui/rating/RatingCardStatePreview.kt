package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.NotRated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Rated
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.SaveError
import com.weather.vibe.feature.viberating.presentation.rating.state.RatingCardUiState.Saving

internal class RatingCardStatePreview : PreviewParameterProvider<RatingCardUiState> {
  override val values: Sequence<RatingCardUiState> = sequenceOf(
    NotRated(sliderDraft = 4, sliderTouched = true),
    Saving(sliderDraft = 4),
    SaveError(sliderDraft = 3),
    Rated(rating = 5)
  )
}
