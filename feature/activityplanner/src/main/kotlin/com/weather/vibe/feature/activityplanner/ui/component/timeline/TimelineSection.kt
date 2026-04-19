package com.weather.vibe.feature.activityplanner.ui.component.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.label.SectionLabelText
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.activityplanner.presentation.state.TimelineHourUiState
import com.weather.vibe.feature.activityplanner.preview.TimelineHoursPreview
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.timeline
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TimelineSection(
  modifier: Modifier = Modifier,
  hours: ImmutableList<TimelineHourUiState>
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    SectionLabelText(
      modifier = Modifier.padding(horizontal = Medium),
      text = timeline(),
      style = typography.titleSmall,
      color = colors.onBackground
    )
    LazyRow(
      contentPadding = PaddingValues(horizontal = Medium),
      horizontalArrangement = Arrangement.spacedBy(Small)
    ) {
      items(items = hours, key = TimelineHourUiState::time) { hour ->
        TimelineHourBar(
          hourLabel = hour.hourLabel,
          score = hour.score,
          tier = hour.tier,
          a11yDescription = hour.contentDescription
        )
      }
    }
    TimelineLegend(
      modifier = Modifier
        .padding(horizontal = Medium)
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(TimelineHoursPreview::class)
  hours: ImmutableList<TimelineHourUiState>
) {
  WeatherVibeTheme {
    TimelineSection(hours = hours)
  }
}
