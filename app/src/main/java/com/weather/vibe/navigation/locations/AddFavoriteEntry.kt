package com.weather.vibe.navigation.locations

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.core.androidext.showShortToast
import com.weather.vibe.domain.location.usecase.AddFavorite.Companion.FAVORITES_LIMIT
import com.weather.vibe.feature.locations.R
import com.weather.vibe.feature.search.presentation.SearchMode
import com.weather.vibe.feature.search.ui.screen.SearchScreen

@Composable
internal fun AddFavoriteEntry(backStack: NavBackStack<NavKey>) {

  val context = LocalContext.current

  SearchScreen(
    mode = SearchMode.Favorites,
    onNavigateBack = { backStack.removeLastOrNull() },
    onFavoritesLimitReached = {
      context.showShortToast(R.string.locations_snackbar_limit_reached, FAVORITES_LIMIT)
    }
  )
}
