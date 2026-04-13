package com.weather.vibe.feature.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.MutedOnBrandAlpha

internal object SplashTextStyles {

  @Composable
  fun onBrand(): Color =
    colors.onAccent

  @Composable
  fun mutedOnBrand(): Color =
    colors.onAccent.copy(alpha = MutedOnBrandAlpha)
}
