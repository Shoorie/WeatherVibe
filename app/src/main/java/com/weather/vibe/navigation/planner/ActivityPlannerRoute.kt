package com.weather.vibe.navigation.planner

import androidx.navigation3.runtime.NavKey
import com.weather.vibe.domain.location.model.Location
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPlannerRoute(
  val selectedLocation: Location
) : NavKey
