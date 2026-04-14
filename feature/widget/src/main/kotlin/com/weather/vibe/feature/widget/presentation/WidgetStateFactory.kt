package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetNotConfiguredUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetReadyUiState
import com.weather.vibe.feature.widget.presentation.state.WidgetWaitingUiState
import com.weather.vibe.feature.widget.ui.WidgetEmojis
import com.weather.vibe.feature.widget.ui.WidgetResources
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
internal class WidgetStateFactory(private val resources: WidgetResources) {

  fun createNotConfigured(): WidgetNotConfiguredUiState =
    WidgetNotConfiguredUiState(
      body = resources.placeholderBody(),
      emoji = WidgetEmojis.PINNED_LOCATION,
      title = resources.placeholderTitle()
    )

  fun createWaiting(location: Location): WidgetWaitingUiState =
    WidgetWaitingUiState(
      body = resources.waitingBody(location.name),
      emoji = WidgetEmojis.HOURGLASS,
      title = resources.waitingTitle()
    )

  fun createReady(snapshot: WidgetSnapshot): WidgetReadyUiState =
    WidgetReadyUiState(
      conditionEmoji = snapshot.condition.emoji,
      contentDescription = resources.weatherContentDescription(
        snapshot.location.name,
        snapshot.suggestion.mood
      ),
      locationName = snapshot.location.name,
      mood = snapshot.suggestion.mood,
      temperature = resources.temperature(snapshot.currentTemperature.roundToInt()),
      vibeText = snapshot.suggestion.briefText
    )
}
