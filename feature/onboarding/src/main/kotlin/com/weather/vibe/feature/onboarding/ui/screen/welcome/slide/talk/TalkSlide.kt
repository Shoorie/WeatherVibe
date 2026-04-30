package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.BODY_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.BodyToCardGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.CARD_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.ContentHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.ContentTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.HEADLINE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.talk.TalkDefaults.HeadlineToBodyGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredRise
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkBody
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkCaption
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkCardA11y
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkCardOutfit
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkCardPill
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkHeadline
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.talkTemperature

@Composable
internal fun TalkSlide(
  modifier: Modifier = Modifier,
  isSettled: Boolean = true
) {
  Box(modifier = modifier.fillMaxSize()) {
    TalkBackgroundGlow()
    TalkContent(isSettled = isSettled)
  }
}

@Composable
private fun TalkContent(isSettled: Boolean) {
  Column(
    modifier = Modifier
      .padding(horizontal = ContentHorizontal)
      .padding(top = ContentTopPadding),
    verticalArrangement = Arrangement.spacedBy(HeadlineToBodyGap)
  ) {
    TalkHeadline()
    TalkBody()
    Box(modifier = Modifier.padding(top = BodyToCardGap - HeadlineToBodyGap)) {
      TalkHeroCardEntry(isSettled = isSettled)
    }
  }
}

@Composable
private fun TalkHeadline() {
  Text(
    modifier = Modifier
      .staggeredFadeUp(delayMs = HEADLINE_DELAY_MS)
      .semantics { heading() },
    text = talkHeadline(),
    style = typography.headlineLarge
      .copy(fontWeight = ExtraBold),
    color = colors.onSurface
  )
}

@Composable
private fun TalkBody() {
  Text(
    modifier = Modifier.staggeredFadeUp(delayMs = BODY_DELAY_MS),
    text = talkBody(),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant
  )
}

@Composable
private fun TalkHeroCardEntry(isSettled: Boolean) {

  val cardA11y = talkCardA11y()

  TalkHeroCard(
    modifier = Modifier
      .staggeredRise(enabled = isSettled, delayMs = CARD_DELAY_MS)
      .semantics(mergeDescendants = true) { contentDescription = cardA11y },
    caption = talkCaption(),
    outfitText = talkCardOutfit(),
    pillLabel = talkCardPill(),
    temperature = talkTemperature()
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    TalkSlide()
  }
}
