package com.weather.vibe.feature.profile.ui.component.vibe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeAvatarSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeAvatarSmileySize
import com.weather.vibe.feature.profile.ui.ProfileResources.Painters

@Composable
internal fun VibeSmileyAvatar(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clearAndSetSemantics {}
      .size(VibeAvatarSize)
      .clip(CircleShape)
      .background(colors.surfaceVariant),
    contentAlignment = Alignment.Center
  ) {
    Image(
      modifier = Modifier.size(VibeAvatarSmileySize),
      painter = Painters.smiley(),
      contentDescription = null
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeSmileyAvatar()
  }
}
