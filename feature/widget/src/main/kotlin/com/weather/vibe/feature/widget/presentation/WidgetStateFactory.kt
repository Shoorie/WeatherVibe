package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetMessage
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Error
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.NoLocation
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Waiting
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState.Weather
import com.weather.vibe.feature.widget.ui.WidgetEmojis.HOURGLASS
import com.weather.vibe.feature.widget.ui.WidgetEmojis.PINNED_LOCATION
import com.weather.vibe.feature.widget.ui.WidgetEmojis.STORM
import com.weather.vibe.feature.widget.ui.WidgetResources
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class WidgetStateFactory(
  private val resources: WidgetResources,
  private val formatTimestamp: WidgetTimestampFormatter
) {

  fun createNoLocation(): NoLocation =
    NoLocation(
      WidgetMessage(
        body = resources.noLocationBody(),
        emoji = PINNED_LOCATION,
        title = resources.noLocationTitle()
      )
    )

  fun createWaitingFor(location: Location): Waiting =
    Waiting(
      WidgetMessage(
        body = resources.waitingBody(location.name),
        emoji = HOURGLASS,
        title = resources.waitingTitle()
      )
    )

  fun createError(): Error =
    Error(
      WidgetMessage(
        body = resources.errorBody(),
        emoji = STORM,
        title = resources.errorTitle()
      )
    )

  fun createWeather(snapshot: WidgetSnapshot): Weather =
    Weather(
      conditionEmoji = snapshot.condition.emoji,
      conditionLabel = resources.conditionLabel(snapshot.condition),
      contentDescription = resources.weatherContentDescription(
        snapshot.location.name,
        snapshot.mood
      ),
      fetchedAtLabel = formatTimestamp(snapshot.fetchedAtEpochMillis),
      locationId = snapshot.location.id,
      locationName = snapshot.location.name,
      mood = snapshot.mood,
      temperature = resources.temperature(snapshot.currentTemperature.roundToInt())
    )
}
