package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.ObserveCurrentLocation
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.annotation.Factory

@Factory
internal class ObserveWidgetUiState(
  private val observeCurrentLocation: ObserveCurrentLocation,
  private val observeWidgetSnapshot: ObserveWidgetSnapshot,
  private val stateFactory: WidgetStateFactory
) {

  operator fun invoke(): Flow<WidgetUiState> =
    combine(
      observeCurrentLocation(),
      observeWidgetSnapshot(),
      ::resolve
    )
      .distinctUntilChanged()
      .catch { emit(stateFactory.createError()) }

  private fun resolve(location: Location?, snapshot: WidgetSnapshot?): WidgetUiState =
    when {
      location == null -> stateFactory.createNoLocation()
      snapshot == null || snapshot.location.id != location.id ->
        stateFactory.createWaitingFor(location)
      else -> stateFactory.createWeather(snapshot)
    }
}
