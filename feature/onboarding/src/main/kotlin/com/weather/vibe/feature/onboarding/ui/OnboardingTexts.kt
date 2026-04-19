package com.weather.vibe.feature.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.onboarding.R

internal object OnboardingTexts {

  @Composable
  fun headline(): String =
    stringResource(R.string.onboarding_headline)

  @Composable
  fun secondaryCta(): String =
    stringResource(R.string.onboarding_secondary_cta)

  @Composable
  fun privacyNote(): String =
    stringResource(R.string.onboarding_privacy_note)

  @Composable
  fun locationIndicatorA11y(): String =
    stringResource(R.string.onboarding_location_indicator_a11y)
}
