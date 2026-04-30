package com.weather.vibe.feature.onboarding.preview.welcome

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.BRIEF
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.PLACES
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.READY
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.TALK
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.VIBE
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeUiState
import com.weather.vibe.feature.onboarding.preview.welcome.slide.BriefSamples.tones
import com.weather.vibe.feature.onboarding.preview.welcome.slide.PlacesSamples.places
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples.greetings
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples.promises

internal class WelcomePreviewProvider :
  PreviewParameterProvider<WelcomeUiState> {

  private val talk: WelcomeUiState =
    sampleFor(slide = TALK, slideIndex = 0)

  private val brief: WelcomeUiState =
    sampleFor(slide = BRIEF, slideIndex = 1)

  private val vibe: WelcomeUiState =
    sampleFor(slide = VIBE, slideIndex = 2)

  private val places: WelcomeUiState =
    sampleFor(slide = PLACES, slideIndex = 3)

  private val ready: WelcomeUiState =
    sampleFor(slide = READY, slideIndex = 4, isFinal = true)

  override val values: Sequence<WelcomeUiState> =
    sequenceOf(talk, brief, vibe, places, ready)

  private fun sampleFor(
    slide: WelcomeSlide,
    slideIndex: Int,
    isFinal: Boolean = false
  ): WelcomeUiState =
    WelcomeUiState(
      briefTones = tones(),
      ctaLabel = if (isFinal) FINAL_CTA else NEXT_CTA,
      greetings = greetings(),
      isFinalSlide = isFinal,
      places = places(),
      promises = promises(),
      skipVisible = !isFinal,
      slide = slide,
      slideIndex = slideIndex,
      totalSlides = TOTAL_SLIDES
    )

  private companion object {
    const val NEXT_CTA = "Next"
    const val FINAL_CTA = "Set up location"
    const val TOTAL_SLIDES = 5
  }
}
