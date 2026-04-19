package com.weather.vibe.feature.onboarding.presentation

import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.FETCHING_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.IDLE
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.REQUESTING_PERMISSION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.OPEN_SETTINGS
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.USE_MY_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingUiState
import com.weather.vibe.feature.onboarding.ui.OnboardingResources
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.IntensityFetching
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.IntensityIdle
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.IntensityPermanentlyDenied
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingDefaults.IntensityRequesting
import org.koin.core.annotation.Factory

@Factory
internal class OnboardingStateFactory(private val resources: OnboardingResources) {

  fun create(phase: OnboardingPhase): OnboardingUiState =
    OnboardingUiState(
      phase = phase,
      subtitle = resources.subtitleFor(phase),
      primaryLabel = resources.primaryLabelFor(phase),
      primaryAction = phase.toPrimaryAction(),
      primaryEnabled = phase.isPrimaryEnabled(),
      pulseIntensity = phase.pulseIntensity()
    )

  private fun OnboardingPhase.toPrimaryAction(): OnboardingPrimaryAction =
    when (this) {
      PERMISSION_PERMANENTLY_DENIED -> OPEN_SETTINGS
      else -> USE_MY_LOCATION
    }

  private fun OnboardingPhase.isPrimaryEnabled(): Boolean =
    when (this) {
      IDLE, PERMISSION_PERMANENTLY_DENIED -> true
      REQUESTING_PERMISSION, FETCHING_LOCATION -> false
    }

  private fun OnboardingPhase.pulseIntensity(): Float =
    when (this) {
      IDLE -> IntensityIdle
      REQUESTING_PERMISSION -> IntensityRequesting
      FETCHING_LOCATION -> IntensityFetching
      PERMISSION_PERMANENTLY_DENIED -> IntensityPermanentlyDenied
    }
}
