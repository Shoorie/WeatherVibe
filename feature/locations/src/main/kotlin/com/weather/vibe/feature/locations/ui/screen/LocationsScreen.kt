package com.weather.vibe.feature.locations.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.comingSoonBody
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.comingSoonTitle

@Composable
fun LocationsScreen(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.backgroundGradientEnd),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    VibeMessage(
      title = comingSoonTitle(),
      message = comingSoonBody()
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationsScreen()
  }
}
