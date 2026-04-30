package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BODY_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.HEADLINE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.HeaderToBodyGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.briefBody
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.briefHeadline

@Composable
internal fun BriefHeader(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(
      modifier = Modifier
        .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
        .semantics { heading() },
      text = briefHeadline(),
      style = typography.headlineLarge
        .copy(fontWeight = ExtraBold),
      color = colors.onSurface
    )
    Spacer(modifier = Modifier.height(HeaderToBodyGap))
    Text(
      modifier = Modifier.staggeredFadeUp(delayMs = BODY_DELAY_MS),
      text = briefBody(),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefHeader()
  }
}
