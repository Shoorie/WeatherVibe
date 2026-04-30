package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.forLevel
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.LegendLabelGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.LegendSwatchGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.LegendSwatchRadius
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.vibe.VibeDefaults.LegendSwatchSize
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.vibeLegendBest
import com.weather.vibe.feature.onboarding.ui.welcome.WelcomeTexts.vibeLegendWorst

@Composable
internal fun VibeLegend(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(LegendLabelGap),
    verticalAlignment = Alignment.CenterVertically
  ) {
    LegendCaption(text = vibeLegendWorst())
    LegendSwatches()
    LegendCaption(text = vibeLegendBest())
  }
}

@Composable
private fun LegendSwatches() {
  Row(horizontalArrangement = Arrangement.spacedBy(LegendSwatchGap)) {
    for (rating in MIN_RATING..MAX_RATING) {
      Box(
        modifier = Modifier
          .size(LegendSwatchSize)
          .clip(RoundedCornerShape(LegendSwatchRadius))
          .background(forLevel(rating))
      )
    }
  }
}

@Composable
private fun LegendCaption(text: String) {
  Text(
    text = text.uppercase(),
    style = typography.labelSmall,
    color = colors.textTertiary
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    VibeLegend()
  }
}
