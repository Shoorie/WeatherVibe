package com.weather.vibe.feature.profile.ui.screen.licenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mikepenz.aboutlibraries.ui.compose.android.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.feature.profile.R
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.licensesScreenSubtitle
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.licensesScreenTitle

@Composable
fun LicensesScreen(onNavigateBack: () -> Unit) {

  val libraries by produceLibraries(R.raw.aboutlibraries)

  LicensesContent(
    onNavigateBack = onNavigateBack
  ) {
    LibrariesContainer(
      libraries = libraries,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
internal fun LicensesContent(
  modifier: Modifier = Modifier,
  onNavigateBack: () -> Unit,
  content: @Composable ColumnScope.() -> Unit
) {
  VibeScreenScaffold(
    modifier = modifier.background(rememberAppBackgroundBrush()),
    header = {
      VibeScreenHeader(
        title = licensesScreenTitle(),
        subtitle = licensesScreenSubtitle(),
        onBackClicked = onNavigateBack
      )
    },
    content = content
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LicensesContent(
      onNavigateBack = {},
      content = {}
    )
  }
}
