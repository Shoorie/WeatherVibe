package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode.Companion.Polite
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.ui.HomeResources.Texts.findingBetterSuggestionsLabel

@Composable
internal fun BriefingLoadingContent(modifier: Modifier = Modifier) {
  val loadingDescription = findingBetterSuggestionsLabel()
  CircularProgressIndicator(
    modifier = modifier.semantics {
      contentDescription = loadingDescription
      liveRegion = Polite
    },
    color = colors.accent
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingLoadingContent(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Large)
    )
  }
}
