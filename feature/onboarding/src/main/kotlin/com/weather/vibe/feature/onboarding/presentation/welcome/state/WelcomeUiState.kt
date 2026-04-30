package com.weather.vibe.feature.onboarding.presentation.welcome.state

import androidx.compose.runtime.Immutable
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefToneUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlaceCardUiState
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class WelcomeUiState(
  val briefTones: ImmutableList<BriefToneUiState>,
  val ctaLabel: String,
  val greetings: ImmutableList<String>,
  val isFinalSlide: Boolean,
  val places: ImmutableList<PlaceCardUiState>,
  val promises: ImmutableList<String>,
  val skipVisible: Boolean,
  val slide: WelcomeSlide,
  val slideIndex: Int,
  val totalSlides: Int
)
