package com.weather.vibe.feature.widget.glance.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.feature.widget.glance.intent.launchAppAction
import com.weather.vibe.feature.widget.glance.preview.sampleMessageStates
import com.weather.vibe.feature.widget.glance.preview.sampleWeatherState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState

@Composable
internal fun WidgetContent(state: WidgetUiState) {
  WidgetSurface(onClickAction = launchAppAction(state.locationId)) {
    when (state) {
      is WidgetUiState.Weather -> WidgetWeatherLayout(state = state)
      is WidgetUiState.Message -> WidgetMessageLayout(state = state)
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewWeather() {
  WidgetContent(state = sampleWeatherState())
}

@PreviewLightDark
@Composable
private fun PreviewWaiting() {
  WidgetContent(state = sampleMessageStates().waiting)
}

@PreviewLightDark
@Composable
private fun PreviewNoLocation() {
  WidgetContent(state = sampleMessageStates().noLocation)
}

@PreviewLightDark
@Composable
private fun PreviewError() {
  WidgetContent(state = sampleMessageStates().error)
}
