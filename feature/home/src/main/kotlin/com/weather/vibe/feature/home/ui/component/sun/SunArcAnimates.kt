package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween

internal object SunArcAnimates {

  val EnterAnimationSpec: FiniteAnimationSpec<Float> =
    tween(durationMillis = ENTER_ANIMATION_MILLIS, easing = EaseOutCubic)

  private const val ENTER_ANIMATION_MILLIS = 1600
}
