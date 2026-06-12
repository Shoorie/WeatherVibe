package com.weather.vibe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.weather.vibe.core.analytics.AnalyticsLogger
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarScrollBehavior
import com.weather.vibe.core.designsystem.components.navigation.rememberVibeBottomBarScrollBehavior
import com.weather.vibe.feature.locations.ui.screen.LocationsScreen
import com.weather.vibe.feature.profile.ui.screen.ProfileScreen
import com.weather.vibe.feature.profile.ui.screen.licenses.LicensesScreen
import com.weather.vibe.feature.settings.notifications.ui.screen.NotificationsScreen
import com.weather.vibe.feature.settings.personalization.ui.screen.PersonalizationScreen
import com.weather.vibe.feature.viberating.ui.history.VibeHistoryScreen
import com.weather.vibe.navigation.bottombar.WeatherVibeBottomBar
import com.weather.vibe.navigation.home.HomeEntry
import com.weather.vibe.navigation.home.HomeRoute
import com.weather.vibe.navigation.locations.AddLocationFavoriteEntry
import com.weather.vibe.navigation.locations.AddLocationFavoriteRoute
import com.weather.vibe.navigation.locations.LocationsRoute
import com.weather.vibe.navigation.onboarding.LocationOnboardingRoute
import com.weather.vibe.navigation.onboarding.OnboardingEntry
import com.weather.vibe.navigation.onboarding.WelcomeOnboardingEntry
import com.weather.vibe.navigation.onboarding.WelcomeOnboardingRoute
import com.weather.vibe.navigation.planner.ActivityPlannerEntry
import com.weather.vibe.navigation.planner.ActivityPlannerRoute
import com.weather.vibe.navigation.profile.ProfileLicensesRoute
import com.weather.vibe.navigation.profile.ProfileNotificationsRoute
import com.weather.vibe.navigation.profile.ProfilePersonalizationRoute
import com.weather.vibe.navigation.profile.ProfileRoute
import com.weather.vibe.navigation.search.SearchEntry
import com.weather.vibe.navigation.search.SearchRoute
import com.weather.vibe.navigation.splash.SplashEntry
import com.weather.vibe.navigation.splash.SplashRoute
import com.weather.vibe.navigation.viberating.VibeHistoryRoute
import com.weather.vibe.navigation.weather.WeatherDetailsRoute
import com.weather.vibe.navigation.weather.WeatherDetailsScreen
import org.koin.compose.koinInject

@Composable
fun WeatherVibeNavHost(
  modifier: Modifier = Modifier,
  startRoute: NavKey = SplashRoute
) {

  val backStack = rememberNavBackStack(startRoute)
  val scrollBehavior = rememberVibeBottomBarScrollBehavior()
  val currentTopRoute by remember(backStack) { derivedStateOf { backStack.lastOrNull() } }
  var isHomeContentReady by remember { mutableStateOf(false) }
  val analyticsLogger = koinInject<AnalyticsLogger>()

  val showBottomBar by remember {
    derivedStateOf {
      currentTopRoute?.isTopLevel() == true &&
        (currentTopRoute !is HomeRoute || isHomeContentReady)
    }
  }

  LaunchedEffect(currentTopRoute) {
    scrollBehavior.show()
  }

  LaunchedEffect(currentTopRoute) {
    currentTopRoute
      ?.analyticsScreenName()
      ?.let(analyticsLogger::logScreenView)
  }

  BottomBarScaffold(
    modifier = modifier,
    backStack = backStack,
    currentTopRoute = currentTopRoute,
    showBottomBar = showBottomBar,
    scrollBehavior = scrollBehavior
  ) { innerPadding ->
    NavDisplay(
      backStack = backStack,
      scrollBehavior = scrollBehavior,
      innerPadding = innerPadding,
      onHomeContentReady = { isHomeContentReady = true }
    )
  }
}

@Composable
private fun BottomBarScaffold(
  modifier: Modifier = Modifier,
  backStack: NavBackStack<NavKey>,
  currentTopRoute: NavKey?,
  showBottomBar: Boolean,
  scrollBehavior: VibeBottomBarScrollBehavior,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = Color.Transparent,
    contentColor = Color.Unspecified,
    contentWindowInsets = WindowInsets(0),
    bottomBar = {
      if (showBottomBar) {
        WeatherVibeBottomBar(
          backStack = backStack,
          currentTopRoute = currentTopRoute,
          scrollBehavior = scrollBehavior
        )
      }
    }
  ) { innerPadding -> content(innerPadding) }
}

@Composable
private fun NavDisplay(
  backStack: NavBackStack<NavKey>,
  scrollBehavior: VibeBottomBarScrollBehavior,
  innerPadding: PaddingValues,
  onHomeContentReady: () -> Unit
) {
  NavDisplay(
    backStack = backStack,
    modifier = Modifier
      .padding(innerPadding)
      .consumeWindowInsets(innerPadding)
      .nestedScroll(scrollBehavior.nestedScrollConnection),
    onBack = { backStack.removeLastOrNull() },
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = { key ->
      profileEntry(key = key, backStack = backStack)
        ?: rootEntry(
          key = key,
          backStack = backStack,
          onHomeContentReady = onHomeContentReady
        )
    }
  )
}

private fun rootEntry(
  key: NavKey,
  backStack: NavBackStack<NavKey>,
  onHomeContentReady: () -> Unit
): NavEntry<NavKey> =
  when (key) {
    is SplashRoute -> NavEntry(key) { SplashEntry(backStack) }
    is WelcomeOnboardingRoute -> NavEntry(key) { WelcomeOnboardingEntry(backStack) }
    is LocationOnboardingRoute -> NavEntry(key) { OnboardingEntry(backStack) }
    is HomeRoute -> NavEntry(key) {
      HomeEntry(
        route = key,
        backStack = backStack,
        onContentReady = { onHomeContentReady() }
      )
    }
    is WeatherDetailsRoute -> NavEntry(key) { WeatherDetailsScreen(key, backStack) }
    is ActivityPlannerRoute -> NavEntry(key) { ActivityPlannerEntry(key, backStack) }
    is SearchRoute -> NavEntry(key) { SearchEntry(backStack) }
    is LocationsRoute -> NavEntry(key) {
      LocationsScreen(
        onNavigateToSearch = { backStack.add(AddLocationFavoriteRoute) }
      )
    }
    is AddLocationFavoriteRoute -> NavEntry(key) { AddLocationFavoriteEntry(backStack) }
    else -> NavEntry(key) {}
  }

private fun profileEntry(
  key: NavKey,
  backStack: NavBackStack<NavKey>
): NavEntry<NavKey>? =
  when (key) {
    is ProfileRoute -> NavEntry(key) {
      ProfileScreen(
        onOpenLicenses = { backStack.add(ProfileLicensesRoute) },
        onOpenLocations = { backStack.selectTab(LocationsRoute) },
        onOpenNotifications = { backStack.add(ProfileNotificationsRoute) },
        onOpenPersonalization = { backStack.add(ProfilePersonalizationRoute) },
        onOpenVibeHistory = { backStack.add(VibeHistoryRoute) }
      )
    }
    is VibeHistoryRoute -> NavEntry(key) {
      VibeHistoryScreen(
        onNavigateBack = { backStack.removeLastOrNull() }
      )
    }
    is ProfilePersonalizationRoute -> NavEntry(key) {
      PersonalizationScreen(
        onNavigateBack = { backStack.removeLastOrNull() }
      )
    }
    is ProfileNotificationsRoute -> NavEntry(key) {
      NotificationsScreen(
        onNavigateBack = { backStack.removeLastOrNull() }
      )
    }
    is ProfileLicensesRoute -> NavEntry(key) {
      LicensesScreen(
        onNavigateBack = { backStack.removeLastOrNull() }
      )
    }
    else -> null
  }
