package com.weather.vibe.navigation.locations

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.search.presentation.SearchMode
import com.weather.vibe.feature.search.ui.screen.SearchScreen

@Composable
internal fun AddLocationFavoriteEntry(backStack: NavBackStack<NavKey>) {
  SearchScreen(
    mode = SearchMode.Favorites,
    onNavigateBack = { backStack.removeLastOrNull() }
  )
}
