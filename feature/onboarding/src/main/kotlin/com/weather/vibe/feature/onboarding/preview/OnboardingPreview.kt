package com.weather.vibe.feature.onboarding.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.FETCHING_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.IDLE
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.REQUESTING_PERMISSION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.OPEN_SETTINGS
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPrimaryAction.USE_MY_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingUiState

internal class OnboardingPreview :
  PreviewParameterProvider<OnboardingUiState> {

  private val idle: OnboardingUiState = preview(
    phase = IDLE,
    subtitle = "Let us pinpoint your spot — the weather will follow you.",
    primaryLabel = "Use my location",
    primaryAction = USE_MY_LOCATION,
    primaryEnabled = true,
    pulseIntensity = 1.0f
  )

  private val requesting: OnboardingUiState = preview(
    phase = REQUESTING_PERMISSION,
    subtitle = "Waiting for your permission…",
    primaryLabel = "Use my location",
    primaryAction = USE_MY_LOCATION,
    primaryEnabled = false,
    pulseIntensity = 0.7f
  )

  private val fetching: OnboardingUiState = preview(
    phase = FETCHING_LOCATION,
    subtitle = "Pinpointing your location…",
    primaryLabel = "Use my location",
    primaryAction = USE_MY_LOCATION,
    primaryEnabled = false,
    pulseIntensity = 1.4f
  )

  private val permanentlyDenied: OnboardingUiState = preview(
    phase = PERMISSION_PERMANENTLY_DENIED,
    subtitle = "Location access is off. Turn it on in app settings and the weather will find you.",
    primaryLabel = "Open settings",
    primaryAction = OPEN_SETTINGS,
    primaryEnabled = true,
    pulseIntensity = 0.55f
  )

  override val values: Sequence<OnboardingUiState> =
    sequenceOf(idle, requesting, fetching, permanentlyDenied)
}

private fun preview(
  phase: OnboardingPhase,
  subtitle: String,
  primaryLabel: String,
  primaryAction: OnboardingPrimaryAction,
  primaryEnabled: Boolean,
  pulseIntensity: Float
): OnboardingUiState = OnboardingUiState(
  phase = phase,
  subtitle = subtitle,
  primaryLabel = primaryLabel,
  primaryAction = primaryAction,
  primaryEnabled = primaryEnabled,
  pulseIntensity = pulseIntensity
)
