package com.weather.vibe

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.navigation.WeatherVibeNavHost

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)

    enableEdgeToEdge(
      statusBarStyle = resolveBarStyle(),
      navigationBarStyle = resolveBarStyle()
    )

    setContent {
      WeatherVibeTheme {
        WeatherVibeNavHost(
          modifier = Modifier.semantics { testTagsAsResourceId = true }
        )
      }
    }
  }

  private fun resolveBarStyle(): SystemBarStyle {
    val nightMode = resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    return when (nightMode == Configuration.UI_MODE_NIGHT_YES) {
      true -> dark(scrim = TRANSPARENT)
      false -> light(scrim = TRANSPARENT, darkScrim = TRANSPARENT)
    }
  }
}
