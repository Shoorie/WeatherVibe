package com.weather.vibe.navigation.home

import androidx.navigation3.runtime.NavKey
import com.weather.vibe.domain.location.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute(
  val selectedLocation: Location
) : NavKey
