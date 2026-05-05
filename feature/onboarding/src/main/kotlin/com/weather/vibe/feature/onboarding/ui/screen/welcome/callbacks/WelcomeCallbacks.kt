package com.weather.vibe.feature.onboarding.ui.screen.welcome.callbacks

import androidx.compose.runtime.Stable
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.NextClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.NotificationsPermissionResult
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SkipClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SkipNotificationsClick
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeAction.SlideChange
import com.weather.vibe.feature.onboarding.presentation.welcome.WelcomeViewModel

@Stable
internal class WelcomeCallbacks(viewModel: WelcomeViewModel) {
  val onNextClick: () -> Unit = { viewModel.dispatch(NextClick) }
  val onNotificationsPermissionResult: (Boolean) -> Unit = { granted ->
    viewModel.dispatch(NotificationsPermissionResult(granted = granted))
  }
  val onSkipClick: () -> Unit = { viewModel.dispatch(SkipClick) }
  val onSkipNotificationsClick: () -> Unit = { viewModel.dispatch(SkipNotificationsClick) }
  val onSlideChange: (Int) -> Unit = { index -> viewModel.dispatch(SlideChange(index)) }
}
