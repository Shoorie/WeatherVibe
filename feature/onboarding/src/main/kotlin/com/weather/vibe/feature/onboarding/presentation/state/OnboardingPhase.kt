package com.weather.vibe.feature.onboarding.presentation.state

internal enum class OnboardingPhase {
  IDLE,
  REQUESTING_PERMISSION,
  FETCHING_LOCATION,
  PERMISSION_PERMANENTLY_DENIED
}
