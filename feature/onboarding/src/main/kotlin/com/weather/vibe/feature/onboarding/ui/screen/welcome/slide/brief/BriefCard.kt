package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.onboarding.preview.welcome.slide.BriefSamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.BlockGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.CARD_ENTER_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.ChipsTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.MetaStartPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.TONE_ROTATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredRise
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay

@Composable
internal fun BriefCard(
  modifier: Modifier = Modifier,
  tones: ImmutableList<BriefToneUiState>,
  isSettled: Boolean = true
) {

  var toneIndex by remember { mutableIntStateOf(0) }
  LaunchedEffect(tones) {
    while (true) {
      delay(timeMillis = TONE_ROTATION_MS)
      toneIndex = (toneIndex + 1) % tones.size
    }
  }

  Column(
    modifier = modifier
      .staggeredRise(
        enabled = isSettled,
        delayMs = CARD_ENTER_DELAY_MS
      ),
    verticalArrangement = Arrangement.spacedBy(BlockGap)
  ) {
    BriefMetaRow(
      modifier = Modifier
        .padding(start = MetaStartPadding)
    )
    BriefQuoteCard(
      quote = tones[toneIndex].quote,
      toneIndex = toneIndex
    )
    BriefToneChips(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = ChipsTopPadding),
      activeIndex = toneIndex,
      tones = tones
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefCard(tones = BriefSamples.tones())
  }
}
