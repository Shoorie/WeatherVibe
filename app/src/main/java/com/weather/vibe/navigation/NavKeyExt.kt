package com.weather.vibe.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.navigation.home.HomeRoute
import com.weather.vibe.navigation.locations.LocationsRoute
import com.weather.vibe.navigation.profile.ProfileRoute

internal fun NavBackStack<NavKey>.goHome(location: Location) {
  replaceWith(HomeRoute(selectedLocation = location))
}

internal fun NavBackStack<NavKey>.replaceWith(destination: NavKey) {
  clear()
  add(destination)
}

internal fun NavKey.isTopLevel(): Boolean =
  when (this) {
    is HomeRoute,
    is LocationsRoute,
    is ProfileRoute -> true
    else -> false
  }

internal fun NavBackStack<NavKey>.selectTab(target: NavKey) {

  val currentTop = lastOrNull()
  if (currentTop?.sameTabAs(target) == true) return

  popAboveHome()

  if (target !is HomeRoute) {
    add(target)
  }
}

private fun NavBackStack<NavKey>.popAboveHome() {
  while (size > 1 && lastOrNull() !is HomeRoute) {
    removeLastOrNull()
  }
}

private fun NavKey.sameTabAs(other: NavKey): Boolean =
  when (this) {
    is HomeRoute if other is HomeRoute -> true
    is LocationsRoute if other is LocationsRoute -> true
    is ProfileRoute if other is ProfileRoute -> true
    else -> false
  }
