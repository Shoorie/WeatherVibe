package com.weather.vibe.feature.widget.glance.preview

import com.weather.vibe.feature.widget.presentation.state.WidgetMessage
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Error
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.NoLocation
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Waiting
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Weather
import com.weather.vibe.feature.widget.ui.WidgetEmojis.HOURGLASS
import com.weather.vibe.feature.widget.ui.WidgetEmojis.PINNED_LOCATION
import com.weather.vibe.feature.widget.ui.WidgetEmojis.STORM
import com.weather.vibe.feature.widget.ui.WidgetEmojis.SUNNY

internal fun sampleWeatherState(): Weather =
  Weather(
    conditionEmoji = SUNNY,
    conditionLabel = "Clear Sky",
    contentDescription = "Weather for Warsaw, Bright",
    fetchedAtLabel = "12:30",
    locationId = 1L,
    locationName = "Warsaw",
    mood = "Bright",
    temperature = "18°"
  )

internal data class SampleMessageStates(
  val waiting: Waiting,
  val noLocation: NoLocation,
  val error: Error
)

internal fun sampleMessageStates(): SampleMessageStates =
  SampleMessageStates(
    waiting = Waiting(
      WidgetMessage(
        body = "Fresh forecast for Warsaw will land in a moment.",
        emoji = HOURGLASS,
        title = "Waking up the vibe…"
      )
    ),
    noLocation = NoLocation(
      WidgetMessage(
        body = "Open the app and choose a location.",
        emoji = PINNED_LOCATION,
        title = "Pick a city in WeatherVibe"
      )
    ),
    error = Error(
      WidgetMessage(
        body = "Tap to open WeatherVibe and try again.",
        emoji = STORM,
        title = "Couldn't load the vibe"
      )
    )
  )
