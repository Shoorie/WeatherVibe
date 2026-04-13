package com.weather.vibe.feature.home.ui.component.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist

@Composable
internal fun MoodHeroCard(
  modifier: Modifier = Modifier,
  mood: String,
  moodDescription: String
) {

  val baseTitle = typography.bodyLarge
  val titleStyle = remember(baseTitle) { baseTitle.copy(fontWeight = FontWeight.SemiBold) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.card)
      .background(colors.primaryContainer)
      .padding(Padding.Medium),
    verticalArrangement = Arrangement.spacedBy(Padding.ExtraSmall)
  ) {
    Text(
      text = mood,
      style = titleStyle,
      color = colors.onPrimaryContainer
    )
    Text(
      text = moodDescription,
      style = typography.bodySmall,
      color = colors.onPrimaryContainer
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MoodHeroCard(
      modifier = Modifier.padding(Padding.Medium),
      mood = loadedPlaylist.mood,
      moodDescription = loadedPlaylist.moodDescription
    )
  }
}
