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

@Composable
fun WeatherVibeNavHost(modifier: Modifier = Modifier) {

  val backStack = rememberNavBackStack(HomeRoute())

  NavDisplay(
    backStack = backStack,
    modifier = modifier,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = { key ->
      when (key) {
        is HomeRoute -> NavEntry(key) { HomeEntry(key, backStack) }
        WeatherDetailsRoute -> NavEntry(key) { DetailsEntry(backStack) }
        SearchRoute -> NavEntry(key) { SearchEntry(backStack) }
        else -> NavEntry(key) {}
      }
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
    selectedCityName = route.selectedCityName,
    selectedLatitude = route.selectedLatitude,
    selectedLongitude = route.selectedLongitude
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
    onLocationSelected = { cityName, latitude, longitude ->
      backStack.removeLastOrNull()
      backStack.removeLastOrNull()
      backStack.add(HomeRoute(cityName, latitude, longitude))
    },
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}
