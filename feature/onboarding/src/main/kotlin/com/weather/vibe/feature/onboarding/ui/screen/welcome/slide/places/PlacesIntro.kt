package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.BODY_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.ContentHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.HEADLINE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.HeadlineToBodyGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.placesBody
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.placesHeadline

@Composable
internal fun PlacesIntro(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.padding(horizontal = ContentHorizontal),
    verticalArrangement = Arrangement.spacedBy(HeadlineToBodyGap)
  ) {
    Headline()
    Body()
  }
}

@Composable
private fun Headline() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
      .semantics { heading() },
    text = placesHeadline(),
    style = typography.headlineLarge
      .copy(fontWeight = FontWeight.ExtraBold),
    color = colors.onSurface
  )
}

@Composable
private fun Body() {
  Text(
    modifier = Modifier.staggeredFadeUp(delayMs = BODY_DELAY_MS),
    text = placesBody(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PlacesIntro()
  }
}
