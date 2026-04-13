package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerBarCount
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerBarGap
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerBarMaxHeight
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerBarMinHeight
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerBarWidth
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerPhaseOffset
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.EqualizerPulseDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.SplashEqualizerLabel

@Composable
internal fun SplashEqualizer(
  modifier: Modifier = Modifier,
  barColor: Color = colors.accent
) {

  val transition = rememberInfiniteTransition(label = SplashEqualizerLabel)
  val progresses = List(EqualizerBarCount) { index ->
    transition.animateFloat(
      initialValue = index * EqualizerPhaseOffset,
      targetValue = 1f + index * EqualizerPhaseOffset,
      animationSpec = infiniteRepeatable(
        animation = tween(
          durationMillis = EqualizerPulseDuration,
          easing = FastOutSlowInEasing
        ),
        repeatMode = RepeatMode.Reverse
      ),
      label = "bar-$index"
    )
  }

  Row(
    modifier = modifier
      .height(EqualizerBarMaxHeight)
      .clearAndSetSemantics {},
    horizontalArrangement = Arrangement.spacedBy(EqualizerBarGap),
    verticalAlignment = Alignment.CenterVertically
  ) {
    progresses.forEach { progress ->
      val fraction = progress.value % 1f
      val barHeight = lerp(
        start = EqualizerBarMinHeight,
        stop = EqualizerBarMaxHeight,
        fraction = fraction
      )
      Box(
        modifier = Modifier
          .width(EqualizerBarWidth)
          .height(barHeight)
          .clip(shapes.pill)
          .background(barColor)
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box(
      modifier = Modifier
        .background(colors.backgroundGradientStart)
        .padding(40.dp),
      contentAlignment = Alignment.Center
    ) {
      SplashEqualizer()
    }
  }
}
