package com.weather.vibe.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.model.LocationResult
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@OptIn(FlowPreview::class)
@KoinViewModel
internal class SearchViewModel(
  private val stateFactory: SearchStateFactory,
  private val useCases: SearchUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<SearchUiState>(Idle)
  val state: StateFlow<SearchUiState> = _state.asStateFlow()

  private val _query = MutableStateFlow("")
  val query: StateFlow<String> = _query.asStateFlow()

  private val _event = Channel<SearchEvent>()
  val event: Flow<SearchEvent> = _event.receiveAsFlow()

  init {
    loadRecentLocations()
    observeQuery()
  }

  fun dispatch(action: SearchAction) {
    when (action) {
      is BackClick -> onBackClick()
      is LocationSelect -> onLocationSelect(action.location)
      is QueryChange -> onQueryChange(action.query)
    }
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onLocationSelect(location: LocationItemUiState) {
    viewModelScope.launch {
      useCases.saveRecentLocation(stateFactory.toLocationResult(location))
      _event.send(
        NavigateBackWithResult(
          cityName = location.name,
          latitude = location.latitude,
          longitude = location.longitude
        )
      )
    }
  }

  private fun onQueryChange(query: String) {
    _query.update { query }
    if (query.isEmpty()) {
      loadRecentLocations()
    }
  }

  private fun loadRecentLocations() {
    useCases.getRecentLocations()
      .onEach(::onRecentLocationsResult)
      .launchIn(viewModelScope)
  }

  private suspend fun onRecentLocationsResult(result: Result<List<LocationResult>>) {
    val locations = result.getOrNull() ?: return
    onRecentLocationsSuccess(locations)
  }

  private suspend fun onRecentLocationsSuccess(locations: List<LocationResult>) {
    if (locations.isEmpty()) {
      _state.update { Idle }
    } else {
      val items = stateFactory.createItems(locations)
      val enriched = enrichWithTemperatures(items)
      _state.update { Recents(enriched) }
    }
  }

  private fun observeQuery() {
    viewModelScope.launch {
      _query
        .debounce(SEARCH_DEBOUNCE_MS)
        .filter { it.length >= MIN_QUERY_LENGTH }
        .collectLatest { query -> performSearch(query) }
    }
  }

  private suspend fun performSearch(query: String) {
    _state.update { Searching }
    useCases.searchLocation(query).collect { onSearchResult(query, it) }
  }

  private fun onSearchResult(query: String, result: Result<List<LocationResult>>) {
    result
      .onSuccess { onSearchSuccess(query, it) }
      .onFailure { onSearchError(query) }
  }

  private fun onSearchSuccess(query: String, locations: List<LocationResult>) {
    if (locations.isEmpty()) {
      _state.update { Empty(query) }
    } else {
      _state.update { Results(stateFactory.createItems(locations)) }
    }
  }

  private fun onSearchError(query: String) {
    _state.update { Empty(query) }
  }

  private suspend fun enrichWithTemperatures(
    items: List<LocationItemUiState>
  ): List<LocationItemUiState> =
    items.map { item ->
      runCatching {
        val temp = useCases.getCurrentTemperature(item.latitude, item.longitude)
        stateFactory.enrichWithTemperature(item, temp)
      }.getOrDefault(item)
    }

  private fun send(event: SearchEvent) {
    viewModelScope.launch { _event.send(event) }
  }

  private companion object {
    const val MIN_QUERY_LENGTH = 2
    const val SEARCH_DEBOUNCE_MS = 400L
  }
}
