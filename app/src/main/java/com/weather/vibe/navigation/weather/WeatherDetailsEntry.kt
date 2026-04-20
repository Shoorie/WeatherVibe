package com.weather.vibe.navigation.weather

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.home.ui.screen.WeatherDetailsScreen

@Composable
internal fun WeatherDetailsScreen(
  route: WeatherDetailsRoute,
  backStack: NavBackStack<NavKey>
) {
  WeatherDetailsScreen(
    onNavigateBack = { backStack.removeLastOrNull() },
    selectedLocation = route.selectedLocation
  )
}
