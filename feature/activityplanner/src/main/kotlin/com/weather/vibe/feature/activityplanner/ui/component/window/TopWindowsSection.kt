package com.weather.vibe.feature.activityplanner.ui.component.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.weather.vibe.feature.activityplanner.presentation.state.WindowCardUiState
import com.weather.vibe.feature.activityplanner.preview.TopWindowsPreview
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.topWindows
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TopWindowsSection(
  modifier: Modifier = Modifier,
  windows: ImmutableList<WindowCardUiState>
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = Medium),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    SectionLabelText(
      text = topWindows(),
      style = typography.titleSmall,
      color = colors.onBackground
    )
    windows.forEach { window ->
      WindowCard(window = window)
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(TopWindowsPreview::class)
  windows: ImmutableList<WindowCardUiState>
) {
  WeatherVibeTheme {
    TopWindowsSection(windows = windows)
  }
}
