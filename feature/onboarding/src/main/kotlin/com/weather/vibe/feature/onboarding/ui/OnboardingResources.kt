package com.weather.vibe.feature.onboarding.ui

import android.content.Context
import com.weather.vibe.feature.onboarding.R
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.FETCHING_LOCATION
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.IDLE
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.PERMISSION_PERMANENTLY_DENIED
import com.weather.vibe.feature.onboarding.presentation.state.OnboardingPhase.REQUESTING_PERMISSION
import org.koin.core.annotation.Factory

@Factory
internal class OnboardingResources(private val context: Context) {

  fun subtitleFor(phase: OnboardingPhase): String =
    context.getString(
      when (phase) {
        IDLE -> R.string.onboarding_subtitle_idle
        REQUESTING_PERMISSION -> R.string.onboarding_subtitle_requesting
        FETCHING_LOCATION -> R.string.onboarding_subtitle_fetching
        PERMISSION_PERMANENTLY_DENIED -> R.string.onboarding_subtitle_permanently_denied
      }
    )

  fun primaryLabelFor(phase: OnboardingPhase): String =
    context.getString(
      when (phase) {
        PERMISSION_PERMANENTLY_DENIED -> R.string.onboarding_primary_cta_settings
        else -> R.string.onboarding_primary_cta_location
      }
    )
}
