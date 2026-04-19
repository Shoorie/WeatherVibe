package com.weather.vibe.feature.activityplanner.ui.component.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.domain.activityplanner.model.ScoreTier.EXCELLENT
import com.weather.vibe.domain.activityplanner.model.ScoreTier.FAIR
import com.weather.vibe.domain.activityplanner.model.ScoreTier.GOOD
import com.weather.vibe.domain.activityplanner.model.ScoreTier.POOR
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerDefaults.Legend
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.legendExcellent
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.legendFair
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.legendGood
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.legendPoor
import com.weather.vibe.feature.activityplanner.ui.component.window.scoreTierBackground

@Composable
internal fun TimelineLegend(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Medium)
  ) {
    LegendItem(
      tier = EXCELLENT,
      label = legendExcellent()
    )
    LegendItem(
      tier = GOOD,
      label = legendGood()
    )
    LegendItem(
      tier = FAIR,
      label = legendFair()
    )
    LegendItem(
      tier = POOR,
      label = legendPoor()
    )
  }
}

@Composable
private fun LegendItem(tier: ScoreTier, label: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Box(
      modifier = Modifier
        .size(Legend.DotSize)
        .clip(CircleShape)
        .background(scoreTierBackground(tier))
    )
    Text(
      text = label,
      color = colors.onSurfaceVariant,
      style = typography.labelSmall
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    TimelineLegend()
  }
}
