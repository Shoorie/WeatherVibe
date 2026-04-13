package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.ExitDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TaglineAlpha
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TaglineFadeDelay
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.TaglineFadeDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.WordmarkFadeDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.WordmarkSlideInitial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashAnimatables {
  val wordmarkAlpha = Animatable(initialValue = 0f)
  val wordmarkSlide = Animatable(initialValue = WordmarkSlideInitial)
  val taglineAlpha = Animatable(initialValue = 0f)
  val exitAlpha = Animatable(initialValue = 1f)
}

internal suspend fun CoroutineScope.wordmarkFadesIn(anim: SplashAnimatables) {
  launch {
    anim.wordmarkSlide.animateTo(
      targetValue = 0f,
      animationSpec = tween(
        durationMillis = WordmarkFadeDuration,
        easing = FastOutSlowInEasing
      )
    )
  }
  anim.wordmarkAlpha.animateTo(
    targetValue = 1f,
    animationSpec = tween(
      durationMillis = WordmarkFadeDuration,
      easing = FastOutSlowInEasing
    )
  )
}

internal fun CoroutineScope.taglineFadesIn(anim: SplashAnimatables) {
  launch {
    delay(TaglineFadeDelay)
    anim.taglineAlpha.animateTo(
      targetValue = TaglineAlpha,
      animationSpec = tween(
        durationMillis = TaglineFadeDuration,
        easing = FastOutSlowInEasing
      )
    )
  }
}

internal fun CoroutineScope.sceneFadesOut(anim: SplashAnimatables) {
  launch {
    anim.exitAlpha.animateTo(
      targetValue = 0f,
      animationSpec = tween(
        durationMillis = ExitDuration,
        easing = FastOutSlowInEasing
      )
    )
  }
}
