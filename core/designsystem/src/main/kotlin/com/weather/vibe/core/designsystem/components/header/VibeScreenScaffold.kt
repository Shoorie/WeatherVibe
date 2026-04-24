package com.weather.vibe.core.designsystem.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush

@Composable
fun VibeScreenScaffold(
  modifier: Modifier = Modifier,
  header: @Composable () -> Unit,
  content: @Composable ColumnScope.() -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(rememberAppBackgroundBrush())
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
      header()
      content()
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeScreenScaffold(
      header = {
        VibeScreenHeader(
          title = "Twój vibe",
          subtitle = "Jak oceniałeś ostatnie dni",
          onBackClicked = {},
          backContentDescription = "Wstecz"
        )
      },
      content = {}
    )
  }
}
