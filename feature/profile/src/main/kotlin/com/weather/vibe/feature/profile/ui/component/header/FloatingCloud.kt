package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudAlpha
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudFloatDurationMs
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudFloatRangeDp
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudHeight
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudOffsetLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudTransitionLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.HeroDecorationCloudWidth
import com.weather.vibe.feature.profile.ui.ProfileResources.Painters

@Composable
internal fun FloatingCloud(modifier: Modifier = Modifier) {
  val verticalOffsetDp = animateVerticalOffset()
  Image(
    modifier = modifier
      .clearAndSetSemantics {}
      .offset(y = verticalOffsetDp.dp)
      .size(width = HeroDecorationCloudWidth, height = HeroDecorationCloudHeight)
      .alpha(HeroDecorationCloudAlpha),
    painter = Painters.cloudDecoration(),
    contentDescription = null
  )
}

@Composable
private fun animateVerticalOffset(): Float {
  val transition = rememberInfiniteTransition(label = HeroDecorationCloudTransitionLabel)
  val offset by transition.animateFloat(
    initialValue = 0f,
    targetValue = HeroDecorationCloudFloatRangeDp,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = HeroDecorationCloudFloatDurationMs,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = HeroDecorationCloudOffsetLabel
  )
  return offset
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    FloatingCloud()
  }
}
