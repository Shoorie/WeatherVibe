package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.rememberBreathingScale
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.GLOW_ALPHA_CENTER
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.GLOW_BREATH_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.GLOW_BREATH_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.GlowDiameter

@Composable
internal fun TalkBackgroundGlow(modifier: Modifier = Modifier) {

  val accent = colors.accent
  val glowBrush = remember(accent) {
    Brush.radialGradient(
      colors = listOf(
        accent.copy(alpha = GLOW_ALPHA_CENTER),
        Color.Transparent
      )
    )
  }
  val scale by rememberBreathingScale(
    durationMs = GLOW_BREATH_MS,
    maxScale = GLOW_BREATH_SCALE
  )

  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.TopCenter
  ) {
    Box(
      modifier = Modifier
        .size(GlowDiameter)
        .graphicsLayer {
          scaleX = scale
          scaleY = scale
        }
        .clip(CircleShape)
        .background(glowBrush)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    TalkBackgroundGlow()
  }
}
