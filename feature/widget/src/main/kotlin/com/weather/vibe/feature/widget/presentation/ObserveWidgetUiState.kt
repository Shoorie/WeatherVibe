package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@OptIn(ExperimentalCoroutinesApi::class)
@Factory
class ObserveWidgetUiState internal constructor(
  private val getRecentLocations: GetRecentLocations,
  private val observeWidgetSnapshot: ObserveWidgetSnapshot,
  private val stateFactory: WidgetStateFactory
) {

  operator fun invoke(): Flow<WidgetUiState> =
    getRecentLocations()
      .flatMapLatest { result -> stateFlowFor(result.getOrNull()?.firstOrNull()) }
      .catch { emit(stateFactory.createNoLocation()) }

  private fun stateFlowFor(location: Location?): Flow<WidgetUiState> =
    when (location) {
      null -> flowOf(stateFactory.createNoLocation())
      else -> observeWidgetSnapshot(location.id).map { snapshot ->
        snapshot?.let(stateFactory::createReady) ?: stateFactory.createWaiting(location)
      }
    }
}
