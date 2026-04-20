package com.weather.vibe.navigation.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBar
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarItem
import com.weather.vibe.core.designsystem.components.navigation.VibeBottomBarScrollBehavior
import com.weather.vibe.navigation.bottombar.BottomBarKeys.HOME
import com.weather.vibe.navigation.bottombar.BottomBarKeys.LOCATIONS
import com.weather.vibe.navigation.bottombar.BottomBarKeys.PROFILE
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.homeClickLabel
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.homeLabel
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.locationsClickLabel
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.locationsLabel
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.profileClickLabel
import com.weather.vibe.navigation.bottombar.BottomBarResources.Texts.profileLabel
import com.weather.vibe.navigation.home.HomeRoute
import com.weather.vibe.navigation.locations.LocationsRoute
import com.weather.vibe.navigation.profile.ProfileRoute
import com.weather.vibe.navigation.selectTab
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun WeatherVibeBottomBar(
  backStack: NavBackStack<NavKey>,
  currentTopRoute: NavKey?,
  scrollBehavior: VibeBottomBarScrollBehavior
) {

  val homeRoute by remember(backStack) {
    derivedStateOf {
      backStack
        .firstOrNull { it is HomeRoute }
        as? HomeRoute
    }
  }
  val activeHome = homeRoute ?: return

  val onHomeClick = remember(backStack, activeHome) {
    { backStack.selectTab(activeHome) }
  }
  val onLocationsClick = remember(backStack) {
    { backStack.selectTab(LocationsRoute) }
  }
  val onProfileClick = remember(backStack) {
    { backStack.selectTab(ProfileRoute) }
  }

  VibeBottomBar(
    items = persistentListOf(
      VibeBottomBarItem(
        key = HOME,
        label = homeLabel(),
        onClickLabel = homeClickLabel(),
        icon = Icons.Default.Home,
        isSelected = currentTopRoute is HomeRoute,
        onClick = onHomeClick
      ),
      VibeBottomBarItem(
        key = LOCATIONS,
        label = locationsLabel(),
        onClickLabel = locationsClickLabel(),
        icon = Icons.Default.LocationOn,
        isSelected = currentTopRoute is LocationsRoute,
        onClick = onLocationsClick
      ),
      VibeBottomBarItem(
        key = PROFILE,
        label = profileLabel(),
        onClickLabel = profileClickLabel(),
        icon = Icons.Default.Person,
        isSelected = currentTopRoute is ProfileRoute,
        onClick = onProfileClick
      )
    ),
    scrollBehavior = scrollBehavior
  )
}
