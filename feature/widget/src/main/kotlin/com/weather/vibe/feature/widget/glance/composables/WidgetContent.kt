package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.weather.vibe.feature.widget.glance.intent.launchAppAction
import com.weather.vibe.feature.widget.glance.preview.WidgetPreview
import com.weather.vibe.feature.widget.glance.theme.WidgetPalette
import com.weather.vibe.feature.widget.presentation.state.WidgetNotConfiguredUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState

@Composable
internal fun WidgetContent(state: WidgetUiState) {
  val context = LocalContext.current
  Box(
    modifier = GlanceModifier
      .fillMaxSize()
      .background(WidgetPalette.background)
      .cornerRadius(24.dp)
      .padding(horizontal = 16.dp, vertical = 14.dp)
      .clickable(launchAppAction(context))
  ) {
    when (state) {
      is WidgetNotConfiguredUiState -> WidgetPlaceholder(state = state)
      is WidgetWaitingUiState -> WidgetWaitingLayout(state = state)
      is WidgetReadyUiState -> WidgetReadyLayout(state = state)
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WidgetPreview::class)
  state: WidgetUiState
) {
  WidgetContent(state = state)
}
