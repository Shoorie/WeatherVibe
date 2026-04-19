package com.weather.vibe.feature.activityplanner.ui.component.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
internal fun ActivityPlannerEmptyMessage(
  modifier: Modifier = Modifier,
  message: String
) {
  VibeMessage(
    modifier = modifier,
    message = message,
    announceLive = true
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ActivityPlannerEmptyMessage(
      message = "No great windows for running today."
    )
  }
}
