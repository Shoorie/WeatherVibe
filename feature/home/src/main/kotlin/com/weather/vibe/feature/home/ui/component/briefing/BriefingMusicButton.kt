package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.IconActionButton
import com.weather.vibe.core.designsystem.theme.AppDimens.ActionButton
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.moodPlaylistContentDescription
import com.weather.vibe.feature.home.ui.HomePainters.musicIcon

@Composable
internal fun BriefingMusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  IconActionButton(
    modifier = modifier,
    icon = musicIcon(),
    contentDescription = moodPlaylistContentDescription(),
    onClick = onClick,
    containerColor = colors.accent,
    contentColor = colors.onAccent,
    containerSize = ActionButton.SmallContainer,
    iconSize = IconSize.Small
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingMusicButton(onClick = {})
  }
}
