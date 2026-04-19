package com.weather.vibe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.activityplanner.ui.screen.ActivityPlannerScreen
import com.weather.vibe.feature.home.ui.screen.HomeScreen
import com.weather.vibe.feature.home.ui.screen.WeatherDetailsScreen
import com.weather.vibe.feature.onboarding.ui.screen.OnboardingScreen
import com.weather.vibe.feature.search.ui.screen.SearchScreen
import com.weather.vibe.feature.settings.ui.screen.SettingsScreen
import com.weather.vibe.feature.splash.ui.screen.SplashScreen

@Composable
fun WeatherVibeNavHost(
  modifier: Modifier = Modifier,
  startRoute: NavKey = SplashRoute
) {

  val backStack = rememberNavBackStack(startRoute)

  NavDisplay(
    backStack = backStack,
    modifier = modifier,
    onBack = { backStack.removeLastOrNull() },
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = { key ->
      when (key) {
        is SplashRoute -> NavEntry(key) { SplashEntry(backStack) }
        is LocationOnboardingRoute -> NavEntry(key) { OnboardingEntry(backStack) }
        is HomeRoute -> NavEntry(key) { HomeEntry(key, backStack) }
        is WeatherDetailsRoute -> NavEntry(key) { DetailsEntry(key, backStack) }
        is ActivityPlannerRoute -> NavEntry(key) { ActivityPlannerEntry(key, backStack) }
        is SearchRoute -> NavEntry(key) { SearchEntry(backStack) }
        is SettingsRoute -> NavEntry(key) { SettingsEntry(backStack) }
        else -> NavEntry(key) {}
      }
    }
  )
}

@Composable
private fun SplashEntry(backStack: NavBackStack<NavKey>) {
  SplashScreen(
    onNavigateToHome = { location -> backStack.goHome(location) },
    onNavigateToOnboarding = { backStack.replaceWith(LocationOnboardingRoute) }
  )
}

@Composable
private fun OnboardingEntry(backStack: NavBackStack<NavKey>) {
  OnboardingScreen(
    onNavigateToHome = { location -> backStack.goHome(location) },
    onNavigateToSearch = { backStack.add(SearchRoute) }
  )
}

@Composable
private fun HomeEntry(
  route: HomeRoute,
  backStack: NavBackStack<NavKey>
) {
  HomeScreen(
    onNavigateToActivityPlanner = { backStack.add(ActivityPlannerRoute(route.selectedLocation)) },
    onNavigateToDetails = { backStack.add(WeatherDetailsRoute(route.selectedLocation)) },
    onNavigateToSearch = { backStack.add(SearchRoute) },
    onNavigateToSettings = { backStack.add(SettingsRoute) },
    selectedLocation = route.selectedLocation
  )
}

@Composable
private fun ActivityPlannerEntry(
  route: ActivityPlannerRoute,
  backStack: NavBackStack<NavKey>
) {
  ActivityPlannerScreen(
    onNavigateBack = { backStack.removeLastOrNull() },
    selectedLocation = route.selectedLocation
  )
}

@Composable
private fun DetailsEntry(
  route: WeatherDetailsRoute,
  backStack: NavBackStack<NavKey>
) {
  WeatherDetailsScreen(
    onNavigateBack = { backStack.removeLastOrNull() },
    selectedLocation = route.selectedLocation
  )
}

@Composable
private fun SearchEntry(backStack: NavBackStack<NavKey>) {
  SearchScreen(
    onLocationSelected = { location -> backStack.goHome(location) },
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}

@Composable
private fun SettingsEntry(backStack: NavBackStack<NavKey>) {
  SettingsScreen(
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}

private fun NavBackStack<NavKey>.goHome(location: Location) {
  replaceWith(HomeRoute(selectedLocation = location))
}

private fun NavBackStack<NavKey>.replaceWith(destination: NavKey) {
  clear()
  add(destination)
}
