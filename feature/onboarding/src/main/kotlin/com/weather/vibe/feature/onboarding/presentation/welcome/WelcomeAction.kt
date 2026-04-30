package com.weather.vibe.feature.onboarding.presentation.welcome

internal sealed interface WelcomeAction {
  data object NextClick : WelcomeAction
  data object SkipClick : WelcomeAction
  data class SlideChange(val slideIndex: Int) : WelcomeAction
}
