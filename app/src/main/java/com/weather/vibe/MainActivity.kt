package com.weather.vibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.navigation.WeatherVibeNavHost
import com.weather.vibe.navigation.deeplink.DeepLinkRouteResolver
import com.weather.vibe.navigation.splash.SplashBackdrop
import com.weather.vibe.theme.WeatherVibeAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val deepLinkRouteResolver by inject<DeepLinkRouteResolver>()
  private val startRoute = MutableStateFlow<NavKey?>(null)

  override fun onCreate(savedInstanceState: Bundle?) {

    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)

    enableEdgeToEdge(
      statusBarStyle = resources.systemBarStyle(),
      navigationBarStyle = resources.systemBarStyle()
    )

    splashScreen.setKeepOnScreenCondition { startRoute.value == null }
    handleDeepLinks()

    setContent {
      WeatherVibeAppTheme {
        val value by startRoute.collectAsState()
        when (value) {
          null -> SplashBackdrop()
          else -> WeatherVibeNavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            startRoute = requireNotNull(value)
          )
        }
      }
    }
  }

  private fun handleDeepLinks() {
    lifecycleScope.launch {
      startRoute.value = deepLinkRouteResolver.resolve(intent)
    }
  }
}
