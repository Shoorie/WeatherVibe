package com.weather.vibe.navigation

import androidx.navigation3.runtime.NavKey
import com.weather.vibe.domain.location.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute(
  val selectedLocation: Location? = null
) : NavKey

@Serializable
data object SearchRoute : NavKey

@Serializable
data object SplashRoute : NavKey

@Serializable
data object SettingsRoute : NavKey

@Serializable
data class WeatherDetailsRoute(
  val selectedLocation: Location? = null
) : NavKey
