package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetErrorUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetNoLocationUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis
import com.weather.vibe.feature.widget.ui.WidgetEmojis.HOURGLASS
import com.weather.vibe.feature.widget.ui.WidgetEmojis.PINNED_LOCATION
import com.weather.vibe.feature.widget.ui.WidgetEmojis.STORM
import com.weather.vibe.feature.widget.ui.WidgetResources
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class WidgetStateFactory(
  private val resources: WidgetResources
) {

  fun createNoLocation(): WidgetNoLocationUiState =
    WidgetNoLocationUiState(
      body = resources.noLocationBody(),
      emoji = PINNED_LOCATION,
      title = resources.noLocationTitle()
    )

  fun createWaitingFor(location: Location): WidgetWaitingUiState =
    WidgetWaitingUiState(
      body = resources.waitingBody(location.name),
      emoji = HOURGLASS,
      title = resources.waitingTitle()
    )

  fun createError(): WidgetErrorUiState =
    WidgetErrorUiState(
      body = resources.errorBody(),
      emoji = STORM,
      title = resources.errorTitle()
    )

  fun createReadyFor(snapshot: WidgetSnapshot): WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = snapshot.condition.emoji,
      conditionLabel = snapshot.condition.label,
      contentDescription = resources.weatherContentDescription(
        snapshot.location.name,
        snapshot.suggestion.mood
      ),
      fetchedAtLabel = resources.fetchTimestamp(snapshot.fetchedAtEpochMillis),
      locationId = snapshot.location.id,
      locationName = snapshot.location.name,
      mood = snapshot.suggestion.mood,
      temperature = resources.temperature(snapshot.currentTemperature.roundToInt())
    )
}
