package com.weather.vibe.feature.home.ui.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeDefaults.EmojiMedium
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeTextStyles.semiBold

@Composable
internal fun DetailSectionHeader(
  modifier: Modifier = Modifier,
  emoji: String,
  subtitle: String,
  title: String
) {
  val titleStyle = semiBold(typography.titleMedium)
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = Small)
      .semantics(mergeDescendants = true) { heading() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = emoji,
      fontSize = EmojiMedium
    )
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = titleStyle,
        color = colors.onBackground
      )
      Text(
        text = subtitle,
        style = typography.bodySmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    DetailSectionHeader(
      emoji = wind(),
      title = "Wind",
      subtitle = "Speed, direction and gusts"
    )
  }
}
