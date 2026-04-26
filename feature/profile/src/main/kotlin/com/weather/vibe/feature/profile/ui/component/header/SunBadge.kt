package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationSunAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationSunSize
import com.weather.vibe.feature.profile.ui.ProfileResources.Painters

@Composable
internal fun SunBadge(modifier: Modifier = Modifier) {
  Image(
    modifier = modifier
      .clearAndSetSemantics {}
      .size(HeroDecorationSunSize)
      .alpha(HeroDecorationSunAlpha),
    painter = Painters.sunBadge(),
    contentDescription = null
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SunBadge()
  }
}
