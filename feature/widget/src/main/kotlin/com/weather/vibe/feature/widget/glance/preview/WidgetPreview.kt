package com.weather.vibe.feature.widget.glance.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.presentation.state.WidgetNoLocationUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis.HOURGLASS
import com.weather.vibe.feature.widget.ui.WidgetEmojis.PINNED_LOCATION
import com.weather.vibe.feature.widget.ui.WidgetEmojis.RAINY
import com.weather.vibe.feature.widget.ui.WidgetEmojis.SUNNY

internal class WidgetPreview : PreviewParameterProvider<WidgetUiState> {

  val sunnyReady: WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = SUNNY,
      contentDescription = "Weather for Warsaw, Bright",
      locationId = 1L,
      locationName = "Warsaw",
      mood = "Bright",
      temperature = "18°",
      vibeText = "Sunny day, perfect for a walk and some fresh tunes."
    )

  val rainyReady: WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = RAINY,
      contentDescription = "Weather for Kraków, Cozy",
      locationId = 2L,
      locationName = "Kraków",
      mood = "Cozy",
      temperature = "9°",
      vibeText = "Grab an umbrella, it's pouring — lo-fi playlist time."
    )

  val waiting: WidgetWaitingUiState =
    WidgetWaitingUiState(
      body = "Fresh forecast for Warsaw will land in a moment.",
      emoji = HOURGLASS,
      title = "Waking up the vibe…"
    )

  val noLocation: WidgetNoLocationUiState =
    WidgetNoLocationUiState(
      body = "Open the app and choose a location.",
      emoji = PINNED_LOCATION,
      title = "Pick a city in WeatherVibe"
    )

  override val values: Sequence<WidgetUiState>
    get() = sequenceOf(
      sunnyReady,
      rainyReady,
      waiting,
      noLocation
    )
}
