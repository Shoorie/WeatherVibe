package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MAX_RATING
import com.weather.vibe.core.designsystem.theme.rating.RatingColors.MIN_RATING
import com.weather.vibe.core.designsystem.theme.rating.ratingColor
import com.weather.vibe.feature.viberating.preview.ScaleLabelsRowPreview
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.scaleLabel

@Composable
internal fun ScaleLabelsRow(
  selected: Int,
  activeColor: Color
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    for (rating in MIN_RATING..MAX_RATING) {
      val isSelected = rating == selected
      Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = scaleLabel(rating),
          style = typography.labelSmall,
          color = if (isSelected) activeColor else colors.onSurfaceVariant,
          fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ScaleLabelsRowPreview::class)
  selected: Int
) {
  WeatherVibeTheme {
    ScaleLabelsRow(
      selected = selected,
      activeColor = ratingColor(selected)
    )
  }
}
