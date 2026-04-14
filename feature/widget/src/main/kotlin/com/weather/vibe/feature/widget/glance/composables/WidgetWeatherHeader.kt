package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetTextStyles
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState

@Composable
internal fun WidgetWeatherHeader(
  state: WidgetReadyUiState,
  modifier: GlanceModifier = GlanceModifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = state.conditionEmoji,
      style = WidgetTextStyles.heroEmoji
    )
    Spacer(modifier = GlanceModifier.width(12.dp))
    Column {
      Text(
        text = state.temperature,
        style = WidgetTextStyles.temperature
      )
      Text(
        text = state.locationName,
        style = WidgetTextStyles.caption
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetWeatherHeader(state = WidgetPreview().rainyReady)
}
