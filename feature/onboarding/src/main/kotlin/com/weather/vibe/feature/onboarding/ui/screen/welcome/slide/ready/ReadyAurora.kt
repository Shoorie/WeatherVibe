package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.aurora.AuroraPalette
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.AURORA_BLOB
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.AURORA_PHASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.ACCENT_AURORA_ALPHA
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BlobBlurRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BlobBlurStrong
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BlobDriftXDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BlobDriftYDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_OFFSET_X
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_OFFSET_Y
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LeftBlobSizeDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NO_PULSE_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_OFFSET_X
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_OFFSET_Y
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RightBlobSizeDp

@Composable
internal fun ReadyAurora(modifier: Modifier = Modifier) {

  val accent = colors.accent
  val leftBrush = remember(accent) { blobBrush(accent.copy(alpha = ACCENT_AURORA_ALPHA)) }
  val rightBrush = remember { blobBrush(AuroraPalette.LavenderBlob) }

  Box(modifier = modifier.fillMaxSize()) {
    DriftingBlob(
      size = LeftBlobSizeDp,
      offsetXFraction = LEFT_BLOB_OFFSET_X,
      offsetYFraction = LEFT_BLOB_OFFSET_Y,
      driftXDp = BlobDriftXDp,
      driftYDp = BlobDriftYDp,
      durationMs = LEFT_BLOB_DURATION_MS,
      brush = leftBrush,
      blurRadius = BlobBlurRadius
    )
    DriftingBlob(
      size = RightBlobSizeDp,
      offsetXFraction = RIGHT_BLOB_OFFSET_X,
      offsetYFraction = RIGHT_BLOB_OFFSET_Y,
      driftXDp = -BlobDriftXDp,
      driftYDp = BlobDriftYDp,
      durationMs = RIGHT_BLOB_DURATION_MS,
      brush = rightBrush,
      blurRadius = BlobBlurStrong
    )
  }
}

@Composable
private fun DriftingBlob(
  size: Dp,
  offsetXFraction: Float,
  offsetYFraction: Float,
  driftXDp: Dp,
  driftYDp: Dp,
  durationMs: Int,
  brush: Brush,
  blurRadius: Dp
) {

  val transition = rememberInfiniteTransition(label = AURORA_BLOB)
  val phase by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMs,
        easing = DecelerateExpressive
      ),
      repeatMode = Reverse
    ),
    label = AURORA_PHASE
  )

  Box(
    modifier = Modifier
      .fillMaxSize()
      .graphicsLayer {
        val originX = this.size.width * offsetXFraction
        val originY = this.size.height * offsetYFraction
        translationX = originX + driftXDp.toPx() * phase
        translationY = originY + driftYDp.toPx() * phase
        scaleX = NO_PULSE_SCALE
        scaleY = NO_PULSE_SCALE
      }
  ) {
    Box(
      modifier = Modifier
        .size(size)
        .blur(blurRadius)
        .clip(CircleShape)
        .background(brush)
    )
  }
}

private fun blobBrush(color: Color): Brush =
  Brush.radialGradient(colors = listOf(color, Color.Transparent))

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadyAurora()
  }
}
