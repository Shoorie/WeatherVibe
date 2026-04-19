package com.weather.vibe.feature.onboarding.presentation

import com.weather.vibe.domain.location.model.Location

internal sealed interface OnboardingEvent {
  data object RequestPermission : OnboardingEvent
  data object OpenAppSettings : OnboardingEvent
  data class NavigateToHome(val location: Location) : OnboardingEvent
  data object NavigateToSearch : OnboardingEvent
}
