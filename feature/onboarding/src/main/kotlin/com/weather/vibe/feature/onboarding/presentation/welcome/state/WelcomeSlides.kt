package com.weather.vibe.feature.onboarding.presentation.welcome.state

internal object WelcomeSlides {

  val ALL: List<WelcomeSlide> = WelcomeSlide.entries
  val LAST_INDEX: Int = ALL.lastIndex

  fun isLast(slideIndex: Int): Boolean =
    slideIndex >= LAST_INDEX
}
