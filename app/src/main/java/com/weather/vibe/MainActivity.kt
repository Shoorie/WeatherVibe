package com.weather.vibe

import android.os.Bundle
import androidx.activity.ComponentActivity
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
    enableEdgeToEdge()
    setContent {
      WeatherVibeTheme {
        WeatherVibeNavHost(
          modifier = Modifier.semantics { testTagsAsResourceId = true }
        )
      }
    }
  }
}
