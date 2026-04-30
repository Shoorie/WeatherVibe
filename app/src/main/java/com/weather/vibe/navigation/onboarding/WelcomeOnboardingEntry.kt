package com.weather.vibe.navigation.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeOnboardingScreen
import com.weather.vibe.navigation.replaceWith

@Composable
internal fun WelcomeOnboardingEntry(backStack: NavBackStack<NavKey>) {
  WelcomeOnboardingScreen(
    onFinishWelcome = { backStack.replaceWith(LocationOnboardingRoute) }
  )
}
