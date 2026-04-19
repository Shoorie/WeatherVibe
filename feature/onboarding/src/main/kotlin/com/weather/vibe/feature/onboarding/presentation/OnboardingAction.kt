package com.weather.vibe.feature.onboarding.presentation

internal sealed interface OnboardingAction {

  data object UseMyLocationClick : OnboardingAction
  data object SearchCityClick : OnboardingAction
  data object OpenSystemSettingsClick : OnboardingAction

  data class PermissionResult(
    val granted: Boolean,
    val canAskAgain: Boolean
  ) : OnboardingAction
}
