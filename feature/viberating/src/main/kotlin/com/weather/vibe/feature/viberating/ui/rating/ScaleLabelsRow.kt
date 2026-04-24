package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.theme.RatingColors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.viberating.ui.VibeRatingResources

@Composable
internal fun ScaleLabelsRow(
  selected: Int,
  activeColor: Color
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    for (rating in RatingColors.MIN_RATING..RatingColors.MAX_RATING) {
      val isSelected = rating == selected
      Text(
        text = VibeRatingResources.scaleLabel(rating),
        style = typography.labelSmall,
        color = if (isSelected) activeColor else colors.onSurfaceVariant,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
      )
    }
  }
}
