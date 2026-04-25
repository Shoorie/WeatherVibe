package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.legendDescription
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.legendHigh
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts.legendLow
import com.weather.vibe.feature.viberating.ui.history.defaults.CalendarLegendDefaults.SwatchShape
import com.weather.vibe.feature.viberating.ui.history.defaults.CalendarLegendDefaults.SwatchSize
import com.weather.vibe.feature.viberating.ui.history.defaults.CalendarLegendDefaults.SwatchSpacing

@Composable
internal fun CalendarLegend(modifier: Modifier = Modifier) {
  val description = legendDescription()
  Row(
    modifier = modifier
      .fillMaxWidth()
      .semantics { contentDescription = description },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = legendLow(),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(Modifier.size(Small))
    LegendSwatchRow()
    Spacer(Modifier.size(Small))
    Text(
      text = legendHigh(),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}

@Composable
private fun LegendSwatchRow() {
  Row(
    horizontalArrangement = Arrangement.spacedBy(SwatchSpacing),
    modifier = Modifier.clearAndSetSemantics {}
  ) {
    for (rating in MIN_RATING..MAX_RATING) {
      Box(
        modifier = Modifier
          .size(SwatchSize)
          .clip(SwatchShape)
          .background(ratingColor(rating))
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    CalendarLegend()
  }
}
