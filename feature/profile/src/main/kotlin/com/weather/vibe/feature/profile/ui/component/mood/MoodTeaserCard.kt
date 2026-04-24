package com.weather.vibe.feature.profile.ui.component.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.pill.VibePill
import com.weather.vibe.core.designsystem.components.surface.VibeCard
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBadge
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodBody
import com.weather.vibe.feature.profile.ui.ProfileResources.Texts.moodTitle
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun MoodTeaserCard(
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
  onClickLabel: String? = null
) {
  VibeCard(
    modifier = modifier,
    contentPadding = Medium,
    onClick = onClick,
    onClickLabel = onClickLabel
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .semantics(mergeDescendants = true) {},
      verticalArrangement = Arrangement.spacedBy(Small)
    ) {
      MoodTeaserTitleRow()
      Text(
        text = moodBody(),
        style = ProfileTextStyles.rowBody(),
        color = colors.onPrimaryContainer
      )
    }
  }
}

@Composable
private fun MoodTeaserTitleRow() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      modifier = Modifier
        .weight(1f)
        .semantics { heading() },
      text = moodTitle(),
      style = ProfileTextStyles.sectionTitle(),
      color = colors.onPrimaryContainer
    )
    VibePill(
      text = moodBadge(),
      containerColor = colors.accent,
      contentColor = colors.onAccent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    MoodTeaserCard()
  }
}
