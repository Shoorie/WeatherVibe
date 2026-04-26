package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudOffsetX
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudOffsetY
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationSunOffsetX
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationSunOffsetY

@Composable
internal fun HeroDecorations(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .clearAndSetSemantics {}
  ) {
    FloatingCloud(
      modifier = Modifier
        .align(Alignment.TopEnd)
        .offset(
          x = HeroDecorationCloudOffsetX,
          y = HeroDecorationCloudOffsetY
        )
    )
    SunBadge(
      modifier = Modifier
        .align(Alignment.TopEnd)
        .offset(
          x = HeroDecorationSunOffsetX,
          y = HeroDecorationSunOffsetY
        )
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    HeroDecorations()
  }
}
