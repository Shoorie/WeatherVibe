package com.weather.vibe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.weather.vibe.feature.home.ui.screen.HomeScreen
import com.weather.vibe.feature.home.ui.screen.WeatherDetailsScreen
import com.weather.vibe.feature.search.ui.SearchScreen
import com.weather.vibe.feature.settings.ui.screen.SettingsScreen
import com.weather.vibe.feature.splash.ui.screen.SplashScreen

@Composable
fun WeatherVibeNavHost(modifier: Modifier = Modifier) {

  val backStack = rememberNavBackStack(SplashRoute)

  NavDisplay(
    backStack = backStack,
    modifier = modifier,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = { key ->
      when (key) {
        is SplashRoute -> NavEntry(key) { SplashEntry(backStack) }
        is HomeRoute -> NavEntry(key) { HomeEntry(key, backStack) }
        is WeatherDetailsRoute -> NavEntry(key) { DetailsEntry(backStack) }
        is SearchRoute -> NavEntry(key) { SearchEntry(backStack) }
        is SettingsRoute -> NavEntry(key) { SettingsEntry(backStack) }
        else -> NavEntry(key) {}
      }
    }
  )
}

@Composable
private fun SplashEntry(backStack: MutableList<NavKey>) {
  SplashScreen(
    onNavigateToHome = {
      backStack.removeLastOrNull()
      backStack.add(HomeRoute())
    }
  )
}

@Composable
private fun HomeEntry(
  route: HomeRoute,
  backStack: MutableList<NavKey>
) {
  HomeScreen(
    onNavigateToDetails = { backStack.add(WeatherDetailsRoute) },
    onNavigateToSearch = { backStack.add(SearchRoute) },
    onNavigateToSettings = { backStack.add(SettingsRoute) },
    selectedLocation = route.selectedLocation
  )
}

@Composable
private fun DetailsEntry(backStack: MutableList<NavKey>) {
  WeatherDetailsScreen(
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}

@Composable
private fun SearchEntry(backStack: MutableList<NavKey>) {
  SearchScreen(
    onLocationSelected = { location ->
      backStack.removeLastOrNull()
      backStack.removeLastOrNull()
      backStack.add(HomeRoute(selectedLocation = location))
    },
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}

@Composable
private fun SettingsEntry(backStack: MutableList<NavKey>) {
  SettingsScreen(
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}
