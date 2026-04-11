package com.weather.vibe.feature.settings.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
internal fun SettingsLoadingState(modifier: Modifier = Modifier) {
  LoadingIndicator(modifier = modifier.fillMaxSize())
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SettingsLoadingState()
  }
}
