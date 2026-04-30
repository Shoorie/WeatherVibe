package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.BRIEF_QUOTE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.CardHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.CardRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.CardVertical
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.MinCardHeight
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.brief.BriefDefaults.QUOTE_CROSSFADE_MS

@Composable
internal fun BriefQuoteCard(
  modifier: Modifier = Modifier,
  quote: String,
  toneIndex: Int
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardRadius))
      .background(colors.popupSurface)
      .defaultMinSize(minHeight = MinCardHeight)
      .padding(
        horizontal = CardHorizontal,
        vertical = CardVertical
      ),
    contentAlignment = Alignment.CenterStart
  ) {
    Crossfade(
      targetState = toneIndex to quote,
      animationSpec = tween(
        durationMillis = QUOTE_CROSSFADE_MS,
        easing = DecelerateExpressive
      ),
      label = BRIEF_QUOTE
    ) { (_, current) ->
      Text(
        text = current,
        style = typography.titleMedium,
        color = colors.onSurface
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefQuoteCard(
      quote = "24° and sunny. A day without drama.",
      toneIndex = 0
    )
  }
}
