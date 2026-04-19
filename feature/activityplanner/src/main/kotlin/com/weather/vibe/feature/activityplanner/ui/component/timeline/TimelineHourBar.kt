package com.weather.vibe.feature.activityplanner.ui.component.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.activityplanner.model.ScoreTier
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import com.weather.vibe.feature.activityplanner.preview.TimelineHourPreview
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerDefaults.Timeline
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerDefaults.Timeline.BarMinHeight
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerDefaults.Timeline.BarWidth
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerDefaults.Timeline.HEIGHT_PER_POINT
import com.weather.vibe.feature.activityplanner.ui.component.window.scoreTierBackground

@Composable
internal fun TimelineHourBar(
  modifier: Modifier = Modifier,
  hourLabel: String,
  score: Int,
  tier: ScoreTier,
  a11yDescription: String
) {
  Column(
    modifier = modifier
      .width(BarWidth)
      .semantics(mergeDescendants = true) { contentDescription = a11yDescription },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    ScoreBar(
      score = score,
      tier = tier
    )
    Text(
      text = hourLabel,
      color = colors.onSurfaceVariant,
      style = typography.labelSmall
    )
  }
}

@Composable
private fun ScoreBar(score: Int, tier: ScoreTier) {

  val fillHeight = remember(score) {
    (BarMinHeight.value + score * HEIGHT_PER_POINT).dp
  }

  Box(
    modifier = Modifier
      .width(BarWidth)
      .height(Timeline.BarTrackHeight)
      .clip(RoundedCornerShape(percent = 40))
      .background(colors.surfaceVariant),
    contentAlignment = Alignment.BottomCenter
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(fillHeight)
        .clip(RoundedCornerShape(percent = 40))
        .background(scoreTierBackground(tier))
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(TimelineHourPreview::class)
  hour: TimelineHourUiState
) {
  WeatherVibeTheme {
    TimelineHourBar(
      hourLabel = hour.hourLabel,
      score = hour.score,
      tier = hour.tier,
      a11yDescription = hour.contentDescription
    )
  }
}
