package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.ExitDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.RingDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TextFadeDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TextSlideInitial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashAnimatables {
  val exitAlpha = Animatable(initialValue = 1f)
  val exitScale = Animatable(initialValue = 1f)
  val glowAlpha = Animatable(initialValue = 0f)
  val glowScale = Animatable(initialValue = 0f)
  val ring1Progress = Animatable(initialValue = 0f)
  val ring2Progress = Animatable(initialValue = 0f)
  val ring3Progress = Animatable(initialValue = 0f)
  val textAlpha = Animatable(initialValue = 0f)
  val textSlide = Animatable(initialValue = TextSlideInitial)
}

internal fun CoroutineScope.glowBurstsIn(anim: SplashAnimatables) {
  launch {
    anim.glowScale.animateTo(
      targetValue = 1f,
      animationSpec = spring(
        dampingRatio = DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
      )
    )
  }
  launch {
    anim.glowAlpha.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 500)
    )
  }
}

internal suspend fun CoroutineScope.ringsExpandSequentially(anim: SplashAnimatables) {
  for (ring in listOf(anim.ring1Progress, anim.ring2Progress, anim.ring3Progress)) {
    delay(SplashDefaults.RingStagger)
    launch {
      ring.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = RingDuration)
      )
    }
  }
}

internal fun CoroutineScope.titleSlidesIn(anim: SplashAnimatables) {
  launch {
    anim.textAlpha.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = TextFadeDuration)
    )
  }
  launch {
    anim.textSlide.animateTo(
      targetValue = 0f,
      animationSpec = spring(dampingRatio = DampingRatioMediumBouncy)
    )
  }
}

internal fun CoroutineScope.screenFadesOut(anim: SplashAnimatables) {
  launch {
    anim.exitAlpha.animateTo(
      targetValue = 0f,
      animationSpec = tween(durationMillis = ExitDuration)
    )
  }
  launch {
    anim.exitScale.animateTo(
      targetValue = SplashDefaults.ExitScale,
      animationSpec = tween(durationMillis = ExitDuration)
    )
  }
}
