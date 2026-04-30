package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode.Restart
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.PARTICLE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.PARTICLE_PHASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.MS_PER_SECOND
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_BASE_DURATION
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_COUNT
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_DELAY_CYCLE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_DELAY_STEP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_DURATION_STEP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_DURATION_VARIANTS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_LEFT_BASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_LEFT_RANGE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_LEFT_STEP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_OPACITY_BASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_OPACITY_RANGE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_OPACITY_STEP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_PERCENT_BASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_RISE_FACTOR
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_SIZE_BASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_SIZE_STEP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.PARTICLE_SIZE_VARIANTS

@Composable
internal fun ReadyParticles(modifier: Modifier = Modifier) {

  val particles = remember { sampleParticles() }

  Box(modifier = modifier.fillMaxSize()) {
    particles.forEach { particle ->
      FloatingParticle(particle = particle)
    }
  }
}

@Composable
private fun FloatingParticle(particle: ReadyParticleUiState) {

  val phase by rememberPhase(particle = particle)

  Box(
    modifier = Modifier
      .fillMaxSize()
      .graphicsLayer {
        val cycle = phase % 1f
        translationX = size.width * particle.leftFraction
        translationY = size.height * (1f - cycle * PARTICLE_RISE_FACTOR)
      }
  ) {
    Box(
      modifier = Modifier
        .size(particle.sizeDp.dp)
        .alpha(particle.opacity)
        .clip(CircleShape)
        .background(colors.accent)
    )
  }
}

@Composable
private fun rememberPhase(particle: ReadyParticleUiState) =
  rememberInfiniteTransition(label = PARTICLE).animateFloat(
    initialValue = particle.delaySeconds / particle.durationSeconds,
    targetValue = 1f + particle.delaySeconds / particle.durationSeconds,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = (particle.durationSeconds * MS_PER_SECOND).toInt(),
        easing = LinearEasing
      ),
      repeatMode = Restart
    ),
    label = PARTICLE_PHASE
  )

private fun sampleParticles(): List<ReadyParticleUiState> =
  List(PARTICLE_COUNT) { index -> particleAt(index = index) }

private fun particleAt(index: Int): ReadyParticleUiState {

  val durationVariant = index % PARTICLE_DURATION_VARIANTS
  val opacityOffset = (index * PARTICLE_OPACITY_STEP) % PARTICLE_OPACITY_RANGE
  val leftOffset = (PARTICLE_LEFT_BASE + (index * PARTICLE_LEFT_STEP) % PARTICLE_LEFT_RANGE)
  val sizeVariant = index % PARTICLE_SIZE_VARIANTS

  return ReadyParticleUiState(
    delaySeconds = (index * PARTICLE_DELAY_STEP) % PARTICLE_DELAY_CYCLE,
    durationSeconds = PARTICLE_BASE_DURATION + durationVariant * PARTICLE_DURATION_STEP,
    leftFraction = leftOffset / PARTICLE_PERCENT_BASE,
    opacity = PARTICLE_OPACITY_BASE + opacityOffset / PARTICLE_PERCENT_BASE,
    sizeDp = PARTICLE_SIZE_BASE + sizeVariant * PARTICLE_SIZE_STEP
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadyParticles()
  }
}
