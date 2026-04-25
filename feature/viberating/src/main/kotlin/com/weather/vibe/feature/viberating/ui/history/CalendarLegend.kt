package com.weather.vibe.feature.viberating.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.R

@Composable
internal fun CalendarLegend(modifier: Modifier = Modifier) {
  val description = stringResource(R.string.vibe_history_legend_description)
  Row(
    modifier = modifier
      .fillMaxWidth()
      .semantics { contentDescription = description },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = stringResource(R.string.vibe_history_legend_low),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(Modifier.size(Padding.Small))
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      modifier = Modifier.clearAndSetSemantics {}
    ) {
      for (rating in MIN_RATING..MAX_RATING) {
        Box(
          modifier = Modifier
            .size(LegendSwatch)
            .clip(RoundedCornerShape(4.dp))
            .background(ratingColor(rating))
        )
      }
    }
    Spacer(Modifier.size(Padding.Small))
    Text(
      text = stringResource(R.string.vibe_history_legend_high),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant
    )
  }
}

private val LegendSwatch = 14.dp
