package com.weather.vibe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weather.vibe.feature.home.ui.screen.HomeScreen
import com.weather.vibe.feature.search.ui.SearchScreen

@Composable
fun WeatherVibeNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController()
) {
  NavHost(
    navController = navController,
    startDestination = HomeRoute,
    modifier = modifier
  ) {
    composable<HomeRoute> { backStackEntry ->

      val cityName by backStackEntry.savedStateHandle
        .getStateFlow<String?>(KEY_CITY_NAME, null)
        .collectAsStateWithLifecycle()

      HomeScreen(
        onNavigateToSearch = { navController.navigate(SearchRoute) },
        selectedCityName = cityName,
        selectedLatitude = backStackEntry.savedStateHandle.get<Double>(KEY_LATITUDE),
        selectedLongitude = backStackEntry.savedStateHandle.get<Double>(KEY_LONGITUDE),
        onSelectionConsumed = { backStackEntry.savedStateHandle[KEY_CITY_NAME] = null }
      )
    }
    composable<SearchRoute> {
      SearchScreen(
        onLocationSelected = { cityName, latitude, longitude ->
          navController.previousBackStackEntry
            ?.savedStateHandle?.apply {
              set(KEY_CITY_NAME, cityName)
              set(KEY_LATITUDE, latitude)
              set(KEY_LONGITUDE, longitude)
            }
          navController.popBackStack()
        },
        onNavigateBack = { navController.popBackStack() }
      )
    }
  }
}

private const val KEY_CITY_NAME = "cityName"
private const val KEY_LATITUDE = "latitude"
private const val KEY_LONGITUDE = "longitude"
