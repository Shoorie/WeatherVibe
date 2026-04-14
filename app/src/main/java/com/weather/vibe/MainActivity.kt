package com.weather.vibe

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.SystemBarStyle.Companion.dark
import androidx.activity.SystemBarStyle.Companion.light
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.domain.location.usecase.GetLocationById
import com.weather.vibe.navigation.HomeRoute
import com.weather.vibe.navigation.SplashRoute
import com.weather.vibe.navigation.WeatherVibeNavHost
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val getLocationById: GetLocationById by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)

    enableEdgeToEdge(
      statusBarStyle = resolveBarStyle(),
      navigationBarStyle = resolveBarStyle()
    )

    var startRouteReady = false
    splashScreen.setKeepOnScreenCondition { !startRouteReady }

    setContent {
      WeatherVibeTheme {
        var startRoute by remember { mutableStateOf<NavKey?>(null) }
        LaunchedEffect(intent) {
          startRoute = resolveStartRoute(intent)
          startRouteReady = true
        }
        when (val resolved = startRoute) {
          null -> Box(
            modifier = Modifier
              .fillMaxSize()
              .background(WeatherVibeTheme.colors.backgroundGradientStart)
          )
          else -> WeatherVibeNavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            startRoute = resolved
          )
        }
      }
    }
  }

  private suspend fun resolveStartRoute(intent: Intent?): NavKey {
    val locationId = intent?.data?.getQueryParameter(LOCATION_ID_QUERY_PARAM)?.toLongOrNull()
      ?: return SplashRoute
    val location = getLocationById(locationId) ?: return SplashRoute
    return HomeRoute(selectedLocation = location)
  }

  private fun resolveBarStyle(): SystemBarStyle {
    val nightMode = resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    return when (nightMode == Configuration.UI_MODE_NIGHT_YES) {
      true -> dark(scrim = TRANSPARENT)
      false -> light(scrim = TRANSPARENT, darkScrim = TRANSPARENT)
    }
  }

  private companion object {
    const val LOCATION_ID_QUERY_PARAM = "locationId"
  }
}
