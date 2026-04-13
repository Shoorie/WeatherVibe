package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.home.ui.HomeDefaults.MusicButtonSize
import com.weather.vibe.feature.home.ui.HomeResources.Painters.musicIcon
import com.weather.vibe.feature.home.ui.HomeResources.Texts.moodPlaylistContentDescription

@Composable
internal fun BriefingMusicButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  val label = moodPlaylistContentDescription()
  Box(
    modifier = modifier
      .minimumInteractiveComponentSize()
      .clip(shapes.pill)
      .clickable(
        onClick = onClick,
        onClickLabel = label,
        role = Role.Button
      ),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(MusicButtonSize)
        .clip(shapes.pill)
        .background(colors.accent),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        painter = musicIcon(),
        contentDescription = null,
        modifier = Modifier.size(IconSize.Small),
        tint = colors.onAccent
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingMusicButton(onClick = {})
  }
}
