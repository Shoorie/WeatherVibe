package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.moodPlaylistErrorTitle
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.moodPlaylistUnavailable
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiError
import com.weather.vibe.feature.home.ui.HomeEmojis as Emojis

@Composable
internal fun MoodLoadingContent(modifier: Modifier = Modifier) {
  LoadingIndicator(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = ExtraLarge)
  )
}

@Composable
internal fun MoodGeneratingContent(
  modifier: Modifier = Modifier,
  message: String
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = ExtraLarge),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    CircularProgressIndicator(color = colors.accent)
    Text(
      text = message,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
internal fun MoodErrorContent(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = ExtraLarge),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = Emojis.error(),
      fontSize = EmojiError
    )
    Spacer(modifier = Modifier.height(Small))
    Text(
      text = moodPlaylistErrorTitle(),
      style = typography.titleSmall,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )
    Text(
      text = moodPlaylistUnavailable(),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
  WeatherVibeTheme {
    MoodLoadingContent(modifier = Modifier.padding(Medium))
  }
}

@PreviewLightDark
@Composable
private fun PreviewGenerating() {
  WeatherVibeTheme {
    MoodGeneratingContent(
      modifier = Modifier.padding(Medium),
      message = "Finding better suggestions…"
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewError() {
  WeatherVibeTheme {
    MoodErrorContent(modifier = Modifier.padding(Medium))
  }
}
