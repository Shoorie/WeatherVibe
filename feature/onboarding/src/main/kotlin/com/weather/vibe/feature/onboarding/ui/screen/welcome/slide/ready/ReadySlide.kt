package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.SlideContentBottomInset
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HeadlineHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NotificationsHorizontalPadding
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ReadySlide(
  modifier: Modifier = Modifier,
  cards: ImmutableList<ReadyNotificationCardUiState>,
  greetings: ImmutableList<String>,
  isSettled: Boolean = true
) {

  val isLandscape = LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE
  val spacing = ReadyOrientationSpacing.forOrientation(isLandscape = isLandscape)
  val scrollState = rememberScrollState()

  Box(modifier = modifier.fillMaxSize()) {
    ReadyAurora()
    ReadyParticles()
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(bottom = SlideContentBottomInset),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top
    ) {
      Spacer(modifier = Modifier.height(spacing.topInset))
      ReadyHello(
        modifier = Modifier.fillMaxWidth(),
        greetings = greetings
      )
      Spacer(modifier = Modifier.height(spacing.helloToHeadline))
      ReadyHeadline(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = HeadlineHorizontal)
      )
      Spacer(modifier = Modifier.height(spacing.headlineToCards))
      ReadyNotificationsPreview(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = NotificationsHorizontalPadding),
        cards = cards,
        isSettled = isSettled
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadySlide(
      cards = ReadySamples.notificationCards(),
      greetings = ReadySamples.greetings()
    )
  }
}
