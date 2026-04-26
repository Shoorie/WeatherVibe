package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.profile.preview.ProfileAvatarPreviewProvider
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroAvatarBorderAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroAvatarBorderWidth
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroAvatarInitialFontSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroAvatarSize

@Composable
internal fun ProfileAvatar(
  modifier: Modifier = Modifier,
  initial: String
) {
  Box(
    modifier = modifier
      .size(HeroAvatarSize)
      .clip(CircleShape)
      .background(colors.accentDark)
      .border(
        width = HeroAvatarBorderWidth,
        color = Color.White.copy(alpha = HeroAvatarBorderAlpha),
        shape = CircleShape
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = initial,
      style = typography.titleLarge.copy(
        fontSize = HeroAvatarInitialFontSize,
        fontWeight = FontWeight.Medium
      ),
      color = colors.onAccent
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfileAvatarPreviewProvider::class)
  initial: String
) {
  WeatherVibeTheme {
    Box(modifier = Modifier.background(colors.accent)) {
      ProfileAvatar(initial = initial)
    }
  }
}
