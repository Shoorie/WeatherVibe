package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.ui.ProfileDefaults.AvatarSize
import com.weather.vibe.feature.profile.ui.ProfileTextStyles

@Composable
internal fun ProfileAvatar(
  initial: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(AvatarSize)
      .clip(CircleShape)
      .background(colors.onAccent.copy(alpha = AVATAR_BACKGROUND_ALPHA)),
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = initial,
      style = ProfileTextStyles.avatarInitial(),
      color = colors.onAccent
    )
  }
}

private const val AVATAR_BACKGROUND_ALPHA = 0.22f

@PreviewLightDark
@Composable
private fun NamedPreview() {
  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(colors.accent)
    ) {
      ProfileAvatar(initial = "A")
    }
  }
}

@PreviewLightDark
@Composable
private fun UnnamedPreview() {
  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(colors.accent)
    ) {
      ProfileAvatar(initial = "?")
    }
  }
}
