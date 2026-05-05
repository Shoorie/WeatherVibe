package com.weather.vibe.feature.onboarding.ui.screen.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.BRIEF
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.PLACES
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.READY
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.TALK
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlide.VIBE
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeSlides
import com.weather.vibe.feature.onboarding.presentation.welcome.state.WelcomeUiState
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefSlide
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesSlide
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadySlide
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkSlide
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeSlide

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WelcomeSlideHost(
  modifier: Modifier = Modifier,
  pagerState: PagerState,
  state: WelcomeUiState
) {

  val settledPage by remember(pagerState) {
    derivedStateOf { pagerState.settledPage }
  }

  HorizontalPager(modifier = modifier, state = pagerState) { page ->
    Box(modifier = Modifier.fillMaxSize()) {
      LavenderBackground()
      RenderSlide(
        slide = WelcomeSlides.ALL[page],
        state = state,
        isSettled = page == settledPage
      )
    }
  }
}

@Composable
private fun RenderSlide(
  slide: WelcomeSlide,
  state: WelcomeUiState,
  isSettled: Boolean
) {
  when (slide) {
    TALK -> TalkSlide(isSettled = isSettled)
    BRIEF -> BriefSlide(tones = state.briefTones, isSettled = isSettled)
    VIBE -> VibeSlide(isSettled = isSettled)
    PLACES -> PlacesSlide(places = state.places, isSettled = isSettled)
    READY -> ReadySlide(
      cards = state.notificationCards,
      greetings = state.greetings,
      isSettled = isSettled
    )
  }
}
