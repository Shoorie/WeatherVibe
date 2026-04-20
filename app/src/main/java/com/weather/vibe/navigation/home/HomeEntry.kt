package com.weather.vibe.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.home.ui.screen.HomeScreen
import com.weather.vibe.navigation.planner.ActivityPlannerRoute
import com.weather.vibe.navigation.profile.ProfilePersonalizationRoute
import com.weather.vibe.navigation.search.SearchRoute
import com.weather.vibe.navigation.weather.WeatherDetailsRoute

@Composable
internal fun HomeEntry(
  route: HomeRoute,
  backStack: NavBackStack<NavKey>,
  onContentReady: () -> Unit
) {
  HomeScreen(
    onNavigateToActivityPlanner = { backStack.add(ActivityPlannerRoute(route.selectedLocation)) },
    onNavigateToDetails = { backStack.add(WeatherDetailsRoute(route.selectedLocation)) },
    onNavigateToSearch = { backStack.add(SearchRoute) },
    onNavigateToSettings = { backStack.add(ProfilePersonalizationRoute) },
    onContentReady = onContentReady,
    selectedLocation = route.selectedLocation
  )
}
