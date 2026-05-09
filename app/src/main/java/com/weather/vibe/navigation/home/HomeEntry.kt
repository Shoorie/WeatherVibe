package com.weather.vibe.navigation.home

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.home.ui.screen.HomeScreen
import com.weather.vibe.feature.widget.glance.intent.PinWidgetLauncher
import com.weather.vibe.navigation.planner.ActivityPlannerRoute
import com.weather.vibe.navigation.profile.ProfilePersonalizationRoute
import com.weather.vibe.navigation.search.SearchRoute
import com.weather.vibe.navigation.viberating.VibeHistoryRoute
import com.weather.vibe.navigation.weather.WeatherDetailsRoute
import org.koin.compose.koinInject

@Composable
internal fun HomeEntry(
  route: HomeRoute,
  backStack: NavBackStack<NavKey>,
  onContentReady: () -> Unit
) {
  val context = LocalContext.current
  val pinWidgetLauncher = koinInject<PinWidgetLauncher>()
  val pinWidgetSupported = remember(pinWidgetLauncher, context) {
    pinWidgetLauncher.isSupported(context)
  }
  HomeScreen(
    onNavigateToActivityPlanner = { backStack.add(ActivityPlannerRoute(route.selectedLocation)) },
    onNavigateToDetails = { backStack.add(WeatherDetailsRoute(route.selectedLocation)) },
    onNavigateToSearch = { backStack.add(SearchRoute) },
    onNavigateToSettings = { backStack.add(ProfilePersonalizationRoute) },
    onNavigateToVibeHistory = { backStack.add(VibeHistoryRoute) },
    onContentReady = onContentReady,
    onPinWidget = { (context as? Activity)?.let(pinWidgetLauncher::pin) },
    isWidgetAlreadyPinned = { pinWidgetLauncher.isAlreadyPinned(context) },
    pinWidgetSupported = pinWidgetSupported,
    selectedLocation = route.selectedLocation
  )
}
