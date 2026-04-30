package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.READY_HELLO
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HELLO_FADE_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HELLO_ROTATION_MS
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay

@Composable
internal fun ReadyHello(
  modifier: Modifier = Modifier,
  greetings: ImmutableList<String>
) {

  var index by remember { mutableIntStateOf(0) }
  LaunchedEffect(greetings) {
    while (true) {
      delay(timeMillis = HELLO_ROTATION_MS)
      index = (index + 1) % greetings.size
    }
  }

  AnimatedContent(
    modifier = modifier.fillMaxWidth(),
    targetState = index,
    transitionSpec = {
      fadeIn(animationSpec = tween(durationMillis = HELLO_FADE_MS)) togetherWith
        fadeOut(animationSpec = tween(durationMillis = HELLO_FADE_MS))
    },
    label = READY_HELLO
  ) { current ->
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = greetings[current].uppercase(),
      style = typography.labelMedium
        .copy(fontWeight = Bold),
      color = colors.onSurface,
      textAlign = TextAlign.Center
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadyHello(greetings = ReadySamples.greetings())
  }
}
