package com.weather.vibe.navigation.search

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.search.ui.screen.SearchScreen
import com.weather.vibe.navigation.goHome

@Composable
internal fun SearchEntry(backStack: NavBackStack<NavKey>) {
  SearchScreen(
    onLocationSelected = { location -> backStack.goHome(location) },
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}
