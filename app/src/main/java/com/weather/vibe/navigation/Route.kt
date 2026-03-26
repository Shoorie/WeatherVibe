package com.weather.vibe.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute(
  val selectedCityName: String? = null,
  val selectedLatitude: Double? = null,
  val selectedLongitude: Double? = null
) : NavKey

@Serializable
data object SearchRoute : NavKey

@Serializable
data object WeatherDetailsRoute : NavKey
