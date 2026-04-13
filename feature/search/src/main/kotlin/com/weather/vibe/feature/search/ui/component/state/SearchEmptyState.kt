package com.weather.vibe.feature.search.ui.component.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.search.ui.SearchResources.Emojis
import com.weather.vibe.feature.search.ui.SearchResources.Texts.emptySubtitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.emptyTitle

@Composable
internal fun SearchEmptyState(
  modifier: Modifier = Modifier,
  query: String
) {
  SearchPromptState(
    modifier = modifier,
    emoji = Emojis.telescope(),
    title = emptyTitle(),
    subtitle = emptySubtitle(query)
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SearchEmptyState(query = "xyzabc")
  }
}
