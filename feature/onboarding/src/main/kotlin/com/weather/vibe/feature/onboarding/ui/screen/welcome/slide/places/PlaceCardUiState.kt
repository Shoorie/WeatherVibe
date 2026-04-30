package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
internal data class PlaceCardUiState(
  val city: String,
  val emoji: String,
  val region: String,
  val tagBackground: Color,
  val tagLabel: String,
  val temperature: String
)
