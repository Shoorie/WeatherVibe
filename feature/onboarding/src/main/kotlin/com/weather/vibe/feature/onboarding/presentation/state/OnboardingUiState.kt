package com.weather.vibe.feature.onboarding.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class OnboardingUiState(
  val phase: OnboardingPhase,
  val subtitle: String,
  val primaryLabel: String,
  val primaryAction: OnboardingPrimaryAction,
  val primaryEnabled: Boolean,
  val pulseIntensity: Float
)
