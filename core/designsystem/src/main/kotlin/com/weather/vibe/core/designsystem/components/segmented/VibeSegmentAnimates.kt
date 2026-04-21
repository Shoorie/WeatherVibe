package com.weather.vibe.core.designsystem.components.segmented

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color

object VibeSegmentAnimates {

  val IndicatorAnimationSpec: FiniteAnimationSpec<Float> =
    tween(durationMillis = ANIMATION_MILLIS)

  val TextColorAnimationSpec: FiniteAnimationSpec<Color> =
    tween(durationMillis = ANIMATION_MILLIS)

  private const val ANIMATION_MILLIS = 250
}
