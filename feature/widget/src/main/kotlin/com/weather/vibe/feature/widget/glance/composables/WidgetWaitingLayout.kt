package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState

@Composable
internal fun WidgetWaitingLayout(state: WidgetWaitingUiState) {
  Column(
    modifier = GlanceModifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = state.emoji,
      style = WidgetTextStyles.heroEmoji
    )
    Spacer(modifier = GlanceModifier.height(6.dp))
    Text(
      text = state.title,
      style = WidgetTextStyles.title
    )
    Text(
      text = state.body,
      style = WidgetTextStyles.caption
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetWaitingLayout(state = WidgetPreview().waiting)
}
