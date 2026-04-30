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
import androidx.compose.ui.unit.dp
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
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CENTER_BLOB_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CENTER_BLOB_OFFSET_X
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CENTER_BLOB_OFFSET_Y
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.CenterBlobSizeDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_OFFSET_X
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LEFT_BLOB_OFFSET_Y
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.LeftBlobSizeDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NO_PULSE_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_OFFSET_X
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RIGHT_BLOB_OFFSET_Y
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.RightBlobSizeDp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.WARM_PULSE_SCALE

@Composable
internal fun ReadyAurora(modifier: Modifier = Modifier) {

  val accent = colors.accent
  val leftBrush = remember(accent) { blobBrush(accent.copy(alpha = ACCENT_AURORA_ALPHA)) }
  val rightBrush = remember { blobBrush(AuroraPalette.LavenderBlob) }
  val centerBrush = remember { blobBrush(AuroraPalette.WarmBlob) }

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
    DriftingBlob(
      size = CenterBlobSizeDp,
      offsetXFraction = CENTER_BLOB_OFFSET_X,
      offsetYFraction = CENTER_BLOB_OFFSET_Y,
      driftXDp = 0.dp,
      driftYDp = 0.dp,
      durationMs = CENTER_BLOB_DURATION_MS,
      brush = centerBrush,
      blurRadius = BlobBlurRadius,
      pulseScale = WARM_PULSE_SCALE
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
  blurRadius: Dp,
  pulseScale: Float = NO_PULSE_SCALE
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
        val scaleFactor = NO_PULSE_SCALE + (pulseScale - NO_PULSE_SCALE) * phase
        scaleX = scaleFactor
        scaleY = scaleFactor
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
