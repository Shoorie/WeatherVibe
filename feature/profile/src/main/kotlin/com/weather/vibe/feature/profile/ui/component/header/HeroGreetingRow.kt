package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.presentation.state.ProfileHeaderUiState
import com.weather.vibe.feature.profile.preview.ProfileHeaderPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroAvatarToTextsSpacing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroGreetingToHandSpacing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroMaxGreetingLines
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroSubtitleAlpha

@Composable
internal fun HeroGreetingRow(
  modifier: Modifier = Modifier,
  header: ProfileHeaderUiState
) {
  val onAccent = colors.onAccent
  val subtitleColor = remember(onAccent) { onAccent.copy(alpha = HeroSubtitleAlpha) }
  Row(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) { heading() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(HeroAvatarToTextsSpacing)
  ) {
    ProfileAvatar(initial = header.avatarInitial)
    GreetingTexts(
      modifier = Modifier.weight(1f),
      greeting = header.greeting,
      showWavingHand = header.showWavingHand,
      subtitle = header.subtitle,
      subtitleColor = subtitleColor
    )
  }
}

@Composable
private fun GreetingTexts(
  modifier: Modifier = Modifier,
  greeting: String,
  showWavingHand: Boolean,
  subtitle: String,
  subtitleColor: Color
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = greeting,
      style = typography.titleLarge.copy(fontWeight = SemiBold),
      color = colors.onAccent,
      maxLines = HeroMaxGreetingLines,
      overflow = TextOverflow.Ellipsis
    )
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(HeroGreetingToHandSpacing)
    ) {
      if (showWavingHand) {
        WavingHand()
      }
      Text(
        text = subtitle,
        style = typography.bodySmall,
        color = subtitleColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfileHeaderPreviewProvider::class)
  header: ProfileHeaderUiState
) {
  WeatherVibeTheme {
    HeroGreetingRow(header = header)
  }
}
