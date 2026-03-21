package com.weather.vibe.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.weather.usecase.GetWeather
import com.weather.vibe.domain.weather.usecase.SearchLocation
import com.weather.vibe.feature.home.presentation.HomeAction.DismissSearch
import com.weather.vibe.feature.home.presentation.HomeAction.LocationSelect
import com.weather.vibe.feature.home.presentation.HomeAction.QueryChange
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ToggleSearch
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@OptIn(FlowPreview::class)
@KoinViewModel
internal class HomeViewModel(
  private val getWeather: GetWeather,
  private val searchLocation: SearchLocation,
  private val stateFactory: HomeStateFactory
) : ViewModel() {

  private val _state = MutableStateFlow<HomeUiState>(Loading)
  val state: StateFlow<HomeUiState> = _state.asStateFlow()

  private val _searchState = MutableStateFlow(SearchState())
  val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

  private val searchQueryFlow = MutableStateFlow("")

  init {
    onRefreshClick()
    observeSearchQuery()
  }

  fun dispatch(action: HomeAction) {
    when (action) {
      is RefreshClick -> onRefreshClick()
      is ToggleSearch -> onToggleSearch()
      is DismissSearch -> onDismissSearch()
      is QueryChange -> onQueryChange(action.query)
      is LocationSelect -> onLocationSelect(action)
    }
  }

  private fun onRefreshClick() {
    loadWeather()
  }

  private fun onToggleSearch() {
    _searchState.update { SearchState(isActive = true) }
    searchQueryFlow.update { "" }
  }

  private fun onDismissSearch() {
    _searchState.update { SearchState() }
    searchQueryFlow.update { "" }
  }

  private fun onQueryChange(query: String) {
    _searchState.update { it.copy(query = query) }
    searchQueryFlow.update { query }
  }

  private fun onLocationSelect(action: LocationSelect) {
    val displayName = buildDisplayName(
      action.result.name,
      action.result.admin1
    )
    onDismissSearch()
    loadWeather(action.result.latitude, action.result.longitude, displayName)
  }

  private fun observeSearchQuery() {
    viewModelScope.launch {
      searchQueryFlow
        .debounce(SEARCH_DEBOUNCE_MS)
        .filter { it.length >= MIN_QUERY_LENGTH }
        .collectLatest { query -> performSearch(query) }
    }
  }

  private suspend fun performSearch(query: String) {
    _searchState.update { it.copy(isSearching = true) }
    searchLocation(query).collect { result ->
      result
        .onSuccess { results ->
          _searchState.update {
            it.copy(isSearching = false, results = results)
          }
        }
        .onFailure {
          _searchState.update {
            it.copy(isSearching = false, results = emptyList())
          }
        }
    }
  }

  private fun loadWeather(
    latitude: Double = DEFAULT_LATITUDE,
    longitude: Double = DEFAULT_LONGITUDE,
    cityName: String = DEFAULT_CITY
  ) {
    _state.update { Loading }
    getWeather(cityName, latitude, longitude)
      .onEach { result ->
        _state.update {
          result.fold(
            onSuccess = stateFactory::create,
            onFailure = { Error(it.message ?: DEFAULT_ERROR) }
          )
        }
      }
      .launchIn(viewModelScope)
  }

  // TODO [azalewski on 21/03/2026]: The domain layer should be responsible for this.
  private fun buildDisplayName(
    name: String,
    admin1: String?
  ): String = buildString {
    append(name)
    if (admin1 != null) append(", $admin1")
  }

  private companion object {
    const val DEFAULT_LATITUDE = 53.0138
    const val DEFAULT_LONGITUDE = 18.5984
    const val DEFAULT_CITY = "Toruń"
    // TODO [azalewski on 21/03/2026]: This should be in resources.
    const val DEFAULT_ERROR = "Unexpected error"
    const val MIN_QUERY_LENGTH = 2
    const val SEARCH_DEBOUNCE_MS = 400L
  }
}
