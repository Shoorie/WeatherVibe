package com.weather.vibe.navigation.weather

import androidx.navigation3.runtime.NavKey
import com.weather.vibe.domain.location.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsRoute(
  val selectedLocation: Location
) : NavKey
