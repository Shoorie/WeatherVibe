package com.weather.vibe.navigation.splash

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.splash.ui.screen.SplashScreen
import com.weather.vibe.navigation.goHome
import com.weather.vibe.navigation.onboarding.LocationOnboardingRoute
import com.weather.vibe.navigation.replaceWith

@Composable
internal fun SplashEntry(backStack: NavBackStack<NavKey>) {
  SplashScreen(
    onNavigateToHome = { location -> backStack.goHome(location) },
    onNavigateToOnboarding = { backStack.replaceWith(LocationOnboardingRoute) }
  )
}
