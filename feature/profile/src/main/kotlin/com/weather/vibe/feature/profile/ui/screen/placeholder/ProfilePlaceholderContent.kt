package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
internal fun ProfilePlaceholderContent(
  modifier: Modifier = Modifier,
  topBarTitle: String,
  topBarSubtitle: String,
  title: String,
  body: String,
  onNavigateBack: () -> Unit
) {
  VibeScreenScaffold(
    modifier = modifier,
    header = {
      VibeScreenHeader(
        title = topBarTitle,
        subtitle = topBarSubtitle,
        onBackClicked = onNavigateBack
      )
    }
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      VibeMessage(title = title, message = body)
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ProfilePlaceholderContent(
      topBarTitle = "Privacy",
      topBarSubtitle = "Your data and permissions",
      title = "Privacy policy",
      body = "WeatherVibe keeps your data on-device. Full policy on the way.",
      onNavigateBack = {}
    )
  }
}
