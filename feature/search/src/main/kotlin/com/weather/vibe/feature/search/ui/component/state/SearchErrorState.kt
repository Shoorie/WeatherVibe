package com.weather.vibe.feature.search.ui.component.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.ui.SearchDefaults.StateEmojiSize
import com.weather.vibe.feature.search.ui.SearchResources.Emojis
import com.weather.vibe.feature.search.ui.SearchResources.Texts.errorTitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.retry

@Composable
internal fun SearchErrorState(
  modifier: Modifier = Modifier,
  message: String,
  onRetry: () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(Padding.Large),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(Small, CenterVertically)
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = Emojis.error(),
      fontSize = StateEmojiSize
    )
    Text(
      text = errorTitle(),
      style = typography.titleMedium,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )
    Text(
      text = message,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
    TextButton(onClick = onRetry) {
      Text(
        text = retry(),
        style = typography.labelMedium,
        color = colors.accent
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SearchErrorState(
      message = "Something went wrong",
      onRetry = {}
    )
  }
}
