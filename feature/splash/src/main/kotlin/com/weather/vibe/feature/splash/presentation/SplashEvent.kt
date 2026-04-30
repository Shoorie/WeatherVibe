package com.weather.vibe.feature.splash.presentation

import com.weather.vibe.domain.location.model.Location

internal sealed interface SplashEvent {
  data class NavigateToHome(val location: Location) : SplashEvent
  data object NavigateToWelcomeOnboarding : SplashEvent
  data object NavigateToLocationOnboarding : SplashEvent
}
