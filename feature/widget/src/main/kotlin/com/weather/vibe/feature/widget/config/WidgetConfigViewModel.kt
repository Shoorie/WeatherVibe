package com.weather.vibe.feature.widget.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.usecase.GetRecentLocations
import com.weather.vibe.domain.widget.usecase.PinWidgetLocation
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Cancel
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Initialize
import com.weather.vibe.feature.widget.config.WidgetConfigAction.LocationSelect
import com.weather.vibe.feature.widget.config.WidgetConfigAction.Retry
import com.weather.vibe.feature.widget.config.WidgetConfigEvent.Finish
import com.weather.vibe.feature.widget.config.helper.GlanceIdResolver
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Error
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Loading
import com.weather.vibe.feature.widget.config.ui.WidgetConfigResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class WidgetConfigViewModel(
  private val getRecentLocations: GetRecentLocations,
  private val glanceIdResolver: GlanceIdResolver,
  private val pinWidgetLocation: PinWidgetLocation,
  private val resources: WidgetConfigResources,
  private val stateFactory: WidgetConfigStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<WidgetConfigUiState>(Loading)
  val state: StateFlow<WidgetConfigUiState> = _state.asStateFlow()

  private val _event = Channel<WidgetConfigEvent>()
  val event: Flow<WidgetConfigEvent> = _event.receiveAsFlow()

  private var appWidgetId: Int = INVALID_APP_WIDGET_ID
  private var recentLocations: List<Location> = emptyList()

  private val errorHandler = CoroutineExceptionHandler { _, _ ->
    showError()
  }

  fun dispatch(action: WidgetConfigAction) {
    when (action) {
      is Initialize -> onInitialize(action.appWidgetId)
      is LocationSelect -> onLocationSelect(action.id)
      is Retry -> loadRecentLocations()
      is Cancel -> send(WidgetConfigEvent.Cancel)
    }
  }

  private fun onInitialize(id: Int) {
    appWidgetId = id
    loadRecentLocations()
  }

  private fun loadRecentLocations() {
    _state.update { Loading }
    getRecentLocations()
      .onEach(::onRecentLocationsResult)
      .launchIn(viewModelScope)
  }

  private fun onRecentLocationsResult(result: Result<List<Location>>) {
    result.fold(
      onSuccess = ::onRecentLocationsSuccess,
      onFailure = { showError() }
    )
  }

  private fun onRecentLocationsSuccess(locations: List<Location>) {
    recentLocations = locations
    _state.update { stateFactory.createReadyOrEmpty(locations) }
  }

  private fun onLocationSelect(id: Long) {
    val location = findLocation(id) ?: return
    viewModelScope.launch(errorHandler) { pinAndFinish(location) }
  }

  private suspend fun pinAndFinish(location: Location) {
    val glanceId = glanceIdResolver.resolve(appWidgetId)
    if (glanceId == null) {
      showError()
      return
    }
    pinWidgetLocation(glanceId, location)
    send(Finish(appWidgetId))
  }

  private fun showError() {
    _state.update { Error(message = resources.defaultError()) }
  }

  private fun findLocation(id: Long): Location? =
    recentLocations.firstOrNull { it.id == id }

  private fun send(event: WidgetConfigEvent) {
    viewModelScope.launch { _event.send(event) }
  }

  private companion object {
    const val INVALID_APP_WIDGET_ID = 0
  }
}
