package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.ChecksBottomPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.ChecksHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HEADLINE_TOP_FRACTION
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HELLO_TOP_FRACTION
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HeadlineHorizontal
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ReadySlide(
  modifier: Modifier = Modifier,
  greetings: ImmutableList<String>,
  promises: ImmutableList<String>
) {
  Box(modifier = modifier.fillMaxSize()) {
    ReadyAurora()
    ReadyParticles()
    ReadyHero(greetings = greetings)
    ReadyChecks(
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(
          start = ChecksHorizontalPadding,
          end = ChecksHorizontalPadding,
          bottom = ChecksBottomPadding
        ),
      promises = promises
    )
  }
}

@Composable
private fun ReadyHero(greetings: ImmutableList<String>) {
  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

    val helloOffsetY = maxHeight * HELLO_TOP_FRACTION
    val headlineOffsetY = maxHeight * HEADLINE_TOP_FRACTION

    ReadyHello(
      modifier = Modifier
        .fillMaxWidth()
        .offset(y = helloOffsetY),
      greetings = greetings
    )
    ReadyHeadline(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = HeadlineHorizontal)
        .offset(y = headlineOffsetY)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadySlide(
      greetings = ReadySamples.greetings(),
      promises = ReadySamples.promises()
    )
  }
}
