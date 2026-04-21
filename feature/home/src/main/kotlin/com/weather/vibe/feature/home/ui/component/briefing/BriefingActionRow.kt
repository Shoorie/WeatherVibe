package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingMusicHint
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.moodPlaylistContentDescription
import com.weather.vibe.feature.home.ui.HomeEmojis.arrowRight
import com.weather.vibe.feature.home.ui.HomeEmojis.headphones

@Composable
internal fun BriefingActionRow(
  modifier: Modifier = Modifier,
  onMusicClick: () -> Unit
) {

  val description = moodPlaylistContentDescription()

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shapes.pill)
      .clickable(
        onClickLabel = description,
        role = Role.Button,
        onClick = onMusicClick
      )
      .padding(vertical = Small)
      .clearAndSetSemantics {},
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = headphones(),
      style = typography.titleSmall
    )
    Spacer(modifier = Modifier.width(Small))
    Text(
      text = aiBriefingMusicHint(),
      style = typography.titleSmall,
      color = colors.accent,
      fontWeight = SemiBold
    )
    Spacer(modifier = Modifier.width(ExtraSmall))
    Text(
      text = arrowRight(),
      style = typography.titleSmall,
      color = colors.accent,
      fontWeight = SemiBold
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Row(
      modifier = Modifier
        .background(colors.primaryContainer)
        .padding(Medium)
    ) {
      BriefingActionRow(onMusicClick = {})
    }
  }
}
