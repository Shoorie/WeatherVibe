package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.SlideContentBottomInset
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.BODY_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.BodyToCardGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.ContentHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.ContentTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.HEADLINE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.HeaderToBodyGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.vibeBody
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.vibeWeekdays
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun VibeSlide(
  modifier: Modifier = Modifier,
  isSettled: Boolean = true
) {

  val weekdayLabels = vibeWeekdays()
  val weekdays = remember(weekdayLabels) { weekdayLabels.toList().toImmutableList() }

  Box(modifier = modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = ContentHorizontal)
        .padding(top = ContentTopPadding, bottom = SlideContentBottomInset),
      verticalArrangement = Arrangement.spacedBy(HeaderToBodyGap)
    ) {
      VibeHeadline()
      VibeBody()
      Spacer(modifier = Modifier.height(BodyToCardGap))
      VibeCalendarCard(
        weekdays = weekdays,
        isSettled = isSettled
      )
    }
  }
}

@Composable
private fun VibeHeadline() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
      .semantics { heading() },
    text = WelcomeTexts.vibeHeadline(),
    style = typography.headlineLarge
      .copy(fontWeight = ExtraBold),
    color = colors.onSurface
  )
}

@Composable
private fun VibeBody() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = BODY_DELAY_MS)
      .fillMaxWidth(),
    text = vibeBody(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeSlide()
  }
}
