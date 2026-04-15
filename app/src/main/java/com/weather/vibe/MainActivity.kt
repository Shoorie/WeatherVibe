package com.weather.vibe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
import com.weather.vibe.navigation.DeepLinkRouteResolver
import com.weather.vibe.navigation.WeatherVibeNavHost
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val deepLinkRouteResolver: DeepLinkRouteResolver by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)

    enableEdgeToEdge(
      statusBarStyle = resources.systemBarStyle(),
      navigationBarStyle = resources.systemBarStyle()
    )

    var startRouteReady = false
    splashScreen.setKeepOnScreenCondition { !startRouteReady }

    setContent {
      WeatherVibeTheme {
        val startRoute = rememberStartRoute(intent) { startRouteReady = true }
        when (startRoute) {
          null -> SplashBackdrop()
          else -> WeatherVibeNavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            startRoute = startRoute
          )
        }
      }
    }
  }

  @Composable
  private fun rememberStartRoute(intent: Intent?, onResolved: () -> Unit): NavKey? {
    var startRoute by remember { mutableStateOf<NavKey?>(null) }
    LaunchedEffect(intent) {
      startRoute = deepLinkRouteResolver.resolve(intent)
      onResolved()
    }
    return startRoute
  }

  @Composable
  private fun SplashBackdrop() {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(WeatherVibeTheme.colors.backgroundGradientStart)
    )
  }
}
