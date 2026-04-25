package com.weather.vibe.feature.viberating.presentation.rating.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class RatingFormDraftUiState(
  val sliderValue: Int,
  val sliderTouched: Boolean,
  val note: String,
  val noteExpanded: Boolean
)
