package com.weather.vibe.feature.onboarding.ui.screen.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors

@Composable
internal fun LavenderBackground(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(colors.backgroundGradientEnd)
  )
}
