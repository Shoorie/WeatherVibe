package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.onboarding.preview.welcome.slide.BriefSamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ContentHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ContentTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.HeaderToCardGap
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun BriefSlide(
  modifier: Modifier = Modifier,
  tones: ImmutableList<BriefToneUiState>,
  isSettled: Boolean = true
) {
  Box(modifier = modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .padding(horizontal = ContentHorizontal)
        .padding(top = ContentTopPadding),
      verticalArrangement = Arrangement.spacedBy(HeaderToCardGap)
    ) {
      BriefHeader()
      BriefCard(tones = tones, isSettled = isSettled)
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefSlide(tones = BriefSamples.tones())
  }
}
