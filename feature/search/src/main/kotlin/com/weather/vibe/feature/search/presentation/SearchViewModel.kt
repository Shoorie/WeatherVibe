package com.weather.vibe.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationWithTemperature
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.presentation.state.withQuery
import com.weather.vibe.feature.search.ui.SearchResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@OptIn(FlowPreview::class)
@KoinViewModel
internal class SearchViewModel(
  private val resources: SearchResources,
  private val stateFactory: SearchStateFactory,
  private val useCases: SearchUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<SearchUiState>(Idle())
  val state: StateFlow<SearchUiState> = _state.asStateFlow()

  private val _event = Channel<SearchEvent>()
  val event: Flow<SearchEvent> = _event.receiveAsFlow()

  private var lastLocations: List<Location> = emptyList()
  private var lastRecentEntries: List<LocationWithTemperature> = emptyList()
  private var temperatureUnit: TemperatureUnit = CELSIUS

  private val errorHandler = CoroutineExceptionHandler { _, _ ->
    showError()
  }

  init {
    observeTemperatureUnit()
    loadRecentLocations()
    observeQueryChanges()
  }

  private fun observeTemperatureUnit() {
    useCases.observeTemperatureUnit()
      .onEach(::onTemperatureUnitChanged)
      .launchIn(viewModelScope)
  }

  private fun onTemperatureUnitChanged(unit: TemperatureUnit) {
    temperatureUnit = unit
    rebuildRecentsIfShown()
  }

  private fun rebuildRecentsIfShown() {

    val current = _state.value
    if (current !is Recents) return

    _state.update {
      Recents(
        query = current.query,
        locations = stateFactory.createItems(
          entries = lastRecentEntries,
          unit = temperatureUnit
        )
      )
    }
  }

  fun dispatch(action: SearchAction) {
    when (action) {
      is BackClick -> onBackClick()
      is LocationSelect -> onLocationSelect(action.id)
      is QueryChange -> onQueryChange(action.query)
      is Retry -> onRetry()
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onLocationSelect(id: Long) {
    val location = findLocation(id) ?: return
    viewModelScope.launch(errorHandler) {
      useCases.saveRecentLocation(location)
      send(NavigateBackWithResult(location))
    }
  }

  private fun onQueryChange(query: String) {
    _state.update { it.withQuery(query) }
    if (query.isEmpty()) loadRecentLocations()
  }

  private fun onRetry() {
    val query = currentQuery()
    when (query.canBeSearched()) {
      true -> retrySearch(query)
      false -> loadRecentLocations()
    }
  }

  private fun loadRecentLocations() {
    useCases.getRecentLocationsWithTemperature()
      .onEach(::onRecentLocationsResult)
      .launchIn(viewModelScope)
  }

  private fun onRecentLocationsResult(result: Result<List<LocationWithTemperature>>) {
    result.fold(
      onSuccess = ::onRecentLocationsSuccess,
      onFailure = { showError() }
    )
  }

  private fun onRecentLocationsSuccess(entries: List<LocationWithTemperature>) {
    lastLocations = entries.map { it.location }
    lastRecentEntries = entries
    _state.update { current ->
      when (entries.isEmpty()) {
        true -> Idle(query = current.query)
        false -> Recents(
          query = current.query,
          locations = stateFactory.createItems(entries = entries, unit = temperatureUnit)
        )
      }
    }
  }

  private fun observeQueryChanges() {
    viewModelScope.launch {
      _state
        .map { it.query }
        .distinctUntilChanged()
        .debounce(SEARCH_DEBOUNCE_MS)
        .collectLatest(::performSearch)
    }
  }

  private suspend fun performSearch(query: String) {

    if (!query.canBeSearched()) return

    _state.update { Searching(query) }

    useCases
      .searchLocation(query)
      .collect(::onSearchResult)
  }

  private fun retrySearch(query: String) {
    viewModelScope.launch { performSearch(query) }
  }

  private fun onSearchResult(result: Result<List<Location>>) {
    result.fold(
      onSuccess = ::onSearchSuccess,
      onFailure = { showError() }
    )
  }

  private fun onSearchSuccess(locations: List<Location>) {

    lastLocations = locations
    val query = currentQuery()
    val entries = locations.map { LocationWithTemperature(location = it) }

    _state.update {
      when (entries.isEmpty()) {
        true -> Empty(query = query)
        false -> Results(
          query = query,
          locations = stateFactory.createItems(entries = entries, unit = temperatureUnit)
        )
      }
    }
  }

  private fun showError() {
    _state.update { current ->
      Error(
        query = current.query,
        message = resources.defaultError()
      )
    }
  }

  private fun currentQuery(): String =
    _state.value.query

  private fun findLocation(id: Long): Location? =
    lastLocations.firstOrNull { it.id == id }

  private fun String.canBeSearched(): Boolean =
    length >= MIN_QUERY_LENGTH

  private fun send(event: SearchEvent) {
    viewModelScope.launch { _event.send(event) }
  }

  private companion object {
    const val MIN_QUERY_LENGTH = 2
    const val SEARCH_DEBOUNCE_MS = 400L
  }
}
