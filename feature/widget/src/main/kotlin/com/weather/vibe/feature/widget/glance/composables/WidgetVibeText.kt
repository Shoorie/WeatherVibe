package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles.body
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles.moodAccent
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.ui.WidgetDefaults.VIBE_MAX_LINES

@Composable
internal fun WidgetVibeText(
  state: WidgetReadyUiState,
  modifier: GlanceModifier = GlanceModifier,
  maxLines: Int = VIBE_MAX_LINES
) {
  Column(modifier = modifier) {
    Text(
      text = state.mood,
      style = moodAccent
    )
    Spacer(modifier = GlanceModifier.height(2.dp))
    Text(
      text = state.vibeText,
      style = body,
      maxLines = maxLines
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetVibeText(state = WidgetPreview().sunnyReady)
}
