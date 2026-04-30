package com.weather.vibe.feature.onboarding.presentation.welcome

internal sealed interface WelcomeEvent {
  data object NavigateToLocationOnboarding : WelcomeEvent
}
