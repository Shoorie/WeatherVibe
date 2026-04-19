package com.weather.vibe.feature.onboarding.ui.screen.callbacks

import androidx.compose.runtime.Stable
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.OpenSystemSettingsClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.PermissionResult
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.SearchCityClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingAction.UseMyLocationClick
import com.weather.vibe.feature.onboarding.presentation.OnboardingViewModel

@Stable
internal class OnboardingCallbacks(viewModel: OnboardingViewModel) {
  val onUseMyLocation: () -> Unit = { viewModel.dispatch(UseMyLocationClick) }
  val onSearchCity: () -> Unit = { viewModel.dispatch(SearchCityClick) }
  val onOpenSettings: () -> Unit = { viewModel.dispatch(OpenSystemSettingsClick) }
  val onPermissionResult: (Boolean, Boolean) -> Unit = { granted, canAskAgain ->
    viewModel.dispatch(PermissionResult(granted = granted, canAskAgain = canAskAgain))
  }
}
