package com.weather.vibe.feature.search.ui.component.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.ui.SearchDefaults.StateEmojiSize

@Composable
internal fun SearchPromptState(
  modifier: Modifier = Modifier,
  emoji: String,
  title: String,
  subtitle: String
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
      text = emoji,
      fontSize = StateEmojiSize
    )
    Text(
      text = title,
      style = typography.titleMedium,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )
    Text(
      text = subtitle,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
  }
}
