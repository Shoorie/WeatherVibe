package com.weather.vibe.feature.activityplanner.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.activityplanner.ui.ActivityPlannerResources.Texts.retry

@Composable
internal fun ActivityPlannerErrorState(
  modifier: Modifier = Modifier,
  message: String,
  onRetryClick: () -> Unit
) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    VibeMessage(
      message = message,
      announceLive = true,
      action = {
        TextButton(onClick = onRetryClick) {
          Text(text = retry())
        }
      }
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    ActivityPlannerErrorState(
      message = "Couldn't load activity plan.",
      onRetryClick = {}
    )
  }
}
