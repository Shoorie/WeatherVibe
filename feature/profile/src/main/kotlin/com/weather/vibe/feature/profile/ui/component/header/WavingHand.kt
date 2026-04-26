package com.weather.vibe.feature.profile.ui.component.header

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandDurationMs
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandEasing
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandEmoji
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandFontSize
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandKeyframes
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandPivot
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandProgressLabel
import com.weather.vibe.feature.profile.ui.ProfileDefaults.WavingHandTransitionLabel

@Composable
internal fun WavingHand(modifier: Modifier = Modifier) {
  val rotationDegrees = animateRotation()
  Text(
    modifier = modifier
      .clearAndSetSemantics {}
      .graphicsLayer(
        rotationZ = rotationDegrees,
        transformOrigin = TransformOrigin(
          pivotFractionX = WavingHandPivot,
          pivotFractionY = WavingHandPivot
        )
      ),
    text = WavingHandEmoji,
    fontSize = WavingHandFontSize
  )
}

@Composable
private fun animateRotation(): Float {
  val transition = rememberInfiniteTransition(label = WavingHandTransitionLabel)
  val progress by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = WavingHandDurationMs,
        easing = WavingHandEasing
      )
    ),
    label = WavingHandProgressLabel
  )
  return wavingHandCurve(progress = progress)
}

private fun wavingHandCurve(progress: Float): Float {
  for (index in 0 until WavingHandKeyframes.lastIndex) {
    val (startProgress, startAngle) = WavingHandKeyframes[index]
    val (endProgress, endAngle) = WavingHandKeyframes[index + 1]
    if (progress in startProgress..endProgress) {
      val span = endProgress - startProgress
      if (span <= 0f) return endAngle
      val fraction = (progress - startProgress) / span
      return startAngle + (endAngle - startAngle) * fraction
    }
  }
  return 0f
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WavingHand()
  }
}
