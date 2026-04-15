package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.weather.vibe.feature.widget.glance.intent.launchAppAction
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette
import com.weather.vibe.feature.widget.presentation.state.WidgetMessageUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState

@Composable
internal fun WidgetContent(state: WidgetUiState) {
  Box(
    modifier = GlanceModifier
      .fillMaxSize()
      .background(WidgetPalette.background)
      .cornerRadius(28.dp)
      .padding(14.dp)
      .clickable(launchAppAction(state.locationIdOrNull()))
  ) {
    when (state) {
      is WidgetReadyUiState -> WidgetReadyLayout(state = state)
      is WidgetMessageUiState -> WidgetMessageLayout(state = state)
    }
  }
}

private fun WidgetUiState.locationIdOrNull(): Long? = when (this) {
  is WidgetReadyUiState -> locationId
  is WidgetMessageUiState -> null
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WidgetPreview::class)
  state: WidgetUiState
) {
  WidgetContent(state = state)
}
