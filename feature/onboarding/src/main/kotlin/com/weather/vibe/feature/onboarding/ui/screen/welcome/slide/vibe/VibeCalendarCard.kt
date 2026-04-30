package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.CARD_ENTER_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.CardHorizontalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.CardRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.CardVerticalPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.SectionGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredRise
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.vibeCalendarTitle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun VibeCalendarCard(
  modifier: Modifier = Modifier,
  weekdays: ImmutableList<String>,
  isSettled: Boolean = true
) {

  val cells = remember { VibeCalendarSample.cells().toImmutableList() }
  val a11y = WelcomeTexts.vibeCalendarA11y()

  Column(
    modifier = modifier
      .staggeredRise(
        enabled = isSettled,
        delayMs = CARD_ENTER_DELAY_MS
      )
      .fillMaxWidth()
      .clip(RoundedCornerShape(CardRadius))
      .background(colors.popupSurface)
      .padding(
        horizontal = CardHorizontalPadding,
        vertical = CardVerticalPadding
      )
      .semantics { contentDescription = a11y },
    verticalArrangement = Arrangement.spacedBy(SectionGap)
  ) {
    VibeCalendarHeader(monthLabel = vibeCalendarTitle())
    VibeCalendarGrid(
      cells = cells,
      weekdays = weekdays,
      isSettled = isSettled
    )
    VibeLegend(modifier = Modifier.align(Alignment.CenterHorizontally))
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeCalendarCard(
      weekdays = persistentListOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
    )
  }
}
