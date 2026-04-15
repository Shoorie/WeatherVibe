package com.weather.vibe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.StateFlow

/**
 * Subscribes Compose to the start-route resolution performed outside the composition.
 *
 * The activity owns the [StateFlow] so the splash screen's pre-composition
 * `setKeepOnScreenCondition` and the first composed frame share the same source of truth:
 * splash stays while the flow holds `null`, and composition switches to the nav host the
 * moment the resolver emits a value.
 */
@Composable
internal fun rememberStartRoute(startRoute: StateFlow<NavKey?>): NavKey? {
  val value by startRoute.collectAsState()
  return value
}
