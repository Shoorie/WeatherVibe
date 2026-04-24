package com.weather.vibe.feature.profile.ui.screen.placeholder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush

@Composable
internal fun ProfilePlaceholderContent(
  modifier: Modifier = Modifier,
  topBarTitle: String,
  title: String,
  body: String,
  onNavigateBack: () -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientEnd,
    contentColor = Color.Unspecified,
    topBar = {
      VibeTopBar(
        title = topBarTitle,
        onNavigateBack = onNavigateBack
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(rememberAppBackgroundBrush()),
      contentAlignment = Alignment.Center
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        VibeMessage(title = title, message = body)
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ProfilePlaceholderContent(
      topBarTitle = "Polityka prywatności",
      title = "Polityka prywatności",
      body = "WeatherVibe trzyma Twoje dane lokalnie, na urządzeniu. Pełna polityka w drodze.",
      onNavigateBack = {}
    )
  }
}
