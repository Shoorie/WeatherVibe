package com.weather.vibe.feature.profile.ui.component.vibe

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeStarsDecorAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeStarsDecorHeight
import com.weather.vibe.feature.profile.ui.ProfileDefaults.VibeStarsDecorWidth
import com.weather.vibe.feature.profile.ui.ProfileResources.Painters

@Composable
internal fun VibeStarsDecor(modifier: Modifier = Modifier) {
  Icon(
    modifier = modifier
      .clearAndSetSemantics {}
      .size(
        width = VibeStarsDecorWidth,
        height = VibeStarsDecorHeight
      )
      .alpha(VibeStarsDecorAlpha),
    painter = Painters.starsDecor(),
    contentDescription = null,
    tint = colors.accent
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeStarsDecor()
  }
}
