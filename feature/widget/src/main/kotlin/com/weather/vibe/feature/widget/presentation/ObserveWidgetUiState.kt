package com.weather.vibe.feature.widget.presentation

import com.weather.vibe.domain.widget.usecase.GetPinnedWidget
import com.weather.vibe.domain.widget.usecase.ObserveWidgetSnapshot
import com.weather.vibe.feature.widget.presentation.state.WidgetUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveWidgetUiState internal constructor(
  private val getPinnedWidget: GetPinnedWidget,
  private val observeWidgetSnapshot: ObserveWidgetSnapshot,
  private val stateFactory: WidgetStateFactory
) {

  operator fun invoke(glanceId: String): Flow<WidgetUiState> = flow {

    val location = getPinnedWidget(glanceId)
    if (location == null) {
      emit(stateFactory.createNotConfigured())
      return@flow
    }

    emitAll(
      observeWidgetSnapshot(location.id)
        .map { snapshot ->
          snapshot?.let(stateFactory::createReady) ?: stateFactory.createWaiting(location)
        }
    )
  }
}
