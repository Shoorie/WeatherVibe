package com.weather.vibe.feature.widget.glance.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.widget.presentation.state.WidgetErrorUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetNoLocationUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis.HOURGLASS
import com.weather.vibe.feature.widget.ui.WidgetEmojis.PINNED_LOCATION
import com.weather.vibe.feature.widget.ui.WidgetEmojis.RAINY
import com.weather.vibe.feature.widget.ui.WidgetEmojis.STORM
import com.weather.vibe.feature.widget.ui.WidgetEmojis.SUNNY

internal class WidgetPreview : PreviewParameterProvider<WidgetUiState> {

  val sunnyReady: WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = SUNNY,
      conditionLabel = "Clear Sky",
      contentDescription = "Weather for Warsaw, Bright",
      fetchedAtLabel = "12:30",
      locationId = 1L,
      locationName = "Warsaw",
      mood = "Bright",
      temperature = "18°"
    )

  val rainyReady: WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = RAINY,
      conditionLabel = "Rain",
      contentDescription = "Weather for Kraków, Cozy",
      fetchedAtLabel = "09:14",
      locationId = 2L,
      locationName = "Kraków",
      mood = "Cozy",
      temperature = "9°"
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

  val error: WidgetErrorUiState =
    WidgetErrorUiState(
      body = "Tap to open WeatherVibe and try again.",
      emoji = STORM,
      title = "Couldn't load the vibe"
    )

  override val values: Sequence<WidgetUiState>
    get() = sequenceOf(
      sunnyReady,
      rainyReady,
      waiting,
      noLocation,
      error
    )
}
