package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BODY_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HEADLINE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HeadlineLeadToBrandGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.HeadlineToBodyGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.startBody
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.startHeadlineBrand
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.startHeadlineLead

@Composable
internal fun ReadyHeadline(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    HeadlineLeadLine()
    Spacer(modifier = Modifier.height(HeadlineLeadToBrandGap))
    HeadlineBrandLine()
    Spacer(modifier = Modifier.height(HeadlineToBodyGap))
    HeadlineBody()
  }
}

@Composable
private fun HeadlineLeadLine() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
      .fillMaxWidth(),
    text = startHeadlineLead(),
    style = typography.displaySmall
      .copy(fontWeight = Light),
    color = colors.onSurface,
    textAlign = TextAlign.Center
  )
}

@Composable
private fun HeadlineBrandLine() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
      .fillMaxWidth(),
    text = startHeadlineBrand(),
    style = typography.displaySmall
      .copy(fontWeight = Bold),
    color = colors.accent,
    textAlign = TextAlign.Center
  )
}

@Composable
private fun HeadlineBody() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = BODY_DELAY_MS)
      .fillMaxWidth(),
    text = startBody(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant,
    textAlign = TextAlign.Center
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ReadyHeadline()
  }
}
