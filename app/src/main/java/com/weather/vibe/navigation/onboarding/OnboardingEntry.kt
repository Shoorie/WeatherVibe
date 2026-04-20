package com.weather.vibe.navigation.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingScreen
import com.weather.vibe.navigation.goHome
import com.weather.vibe.navigation.search.SearchRoute

@Composable
internal fun OnboardingEntry(backStack: NavBackStack<NavKey>) {
  OnboardingScreen(
    onNavigateToHome = { location -> backStack.goHome(location) },
    onNavigateToSearch = { backStack.add(SearchRoute) }
  )
}
