package com.weather.vibe.feature.activityplanner.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme

@Composable
internal fun ActivityPlannerLoadingState(modifier: Modifier = Modifier) {
  LoadingIndicator(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = ExtraLarge)
  )
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ActivityPlannerLoadingState()
  }
}
