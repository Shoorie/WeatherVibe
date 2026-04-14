package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState

@Composable
internal fun WidgetReadyLayout(state: WidgetReadyUiState) {
  Column(modifier = GlanceModifier.fillMaxSize()) {
    WidgetWeatherHeader(state = state)
    Spacer(modifier = GlanceModifier.height(12.dp))
    WidgetVibeText(state = state)
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WidgetReadyLayout(state = WidgetPreview().sunnyReady)
}
