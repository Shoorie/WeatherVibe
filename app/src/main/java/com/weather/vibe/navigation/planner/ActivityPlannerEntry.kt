package com.weather.vibe.navigation.planner

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.feature.activityplanner.ui.screen.ActivityPlannerScreen

@Composable
internal fun ActivityPlannerEntry(
  route: ActivityPlannerRoute,
  backStack: NavBackStack<NavKey>
) {
  ActivityPlannerScreen(
    onNavigateBack = { backStack.removeLastOrNull() },
    selectedLocation = route.selectedLocation
  )
}
