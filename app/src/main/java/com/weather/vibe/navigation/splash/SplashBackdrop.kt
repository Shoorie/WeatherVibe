package com.weather.vibe.navigation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
internal fun SplashBackdrop() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(colors.backgroundGradientStart)
  )
}
