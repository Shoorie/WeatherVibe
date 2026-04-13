package com.weather.vibe.feature.search.ui.component.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.search.ui.SearchResources.Emojis
import com.weather.vibe.feature.search.ui.SearchResources.Texts.idleSubtitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.idleTitle

@Composable
internal fun SearchIdleState(modifier: Modifier = Modifier) {
  SearchPromptState(
    modifier = modifier,
    emoji = Emojis.globe(),
    title = idleTitle(),
    subtitle = idleSubtitle()
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SearchIdleState()
  }
}
