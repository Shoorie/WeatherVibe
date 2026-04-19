package com.weather.vibe.feature.activityplanner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.segmented.VibeSegmentedControl
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.domain.activityplanner.model.ActivityType
import com.weather.vibe.feature.activityplanner.presentation.state.ActivityPlannerUiState.Loaded
import com.weather.vibe.feature.activityplanner.preview.ActivityPlannerLoadedPreview
import com.weather.vibe.feature.activityplanner.ui.component.empty.ActivityPlannerEmptyMessage
import com.weather.vibe.feature.activityplanner.ui.component.timeline.TimelineSection
import com.weather.vibe.feature.activityplanner.ui.component.window.TopWindowsSection

@Composable
internal fun ActivityPlannerLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onActivitySelect: (ActivityType) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .verticalScroll(rememberScrollState())
      .padding(vertical = Medium),
    verticalArrangement = Arrangement.spacedBy(Large)
  ) {
    VibeSegmentedControl(
      modifier = Modifier.padding(horizontal = Medium),
      segments = state.activities,
      onSegmentClick = onActivitySelect
    )
    if (state.emptyMessage != null) {
      ActivityPlannerEmptyMessage(message = state.emptyMessage)
    }
    if (state.topWindows.isNotEmpty()) {
      TopWindowsSection(windows = state.topWindows)
    }
    if (state.timeline.isNotEmpty()) {
      TimelineSection(hours = state.timeline)
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ActivityPlannerLoadedPreview::class)
  state: Loaded
) {
  WeatherVibeTheme {
    ActivityPlannerLoadedContent(
      state = state,
      onActivitySelect = {}
    )
  }
}
