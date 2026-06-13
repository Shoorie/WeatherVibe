package com.weather.vibe.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.HeartClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.PermissionResult
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry
import com.weather.vibe.feature.search.presentation.SearchAction.SetMode
import com.weather.vibe.feature.search.presentation.SearchAction.UseMyLocationClick
import com.weather.vibe.feature.search.presentation.SearchEvent.LimitReached
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.SearchEvent.OpenAppSettings
import com.weather.vibe.feature.search.presentation.SearchEvent.RequestLocationPermission
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.presentation.state.withQuery
import com.weather.vibe.feature.search.ui.SearchResources
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow.DROP_LATEST
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
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

  private val _favoritesCount = MutableStateFlow(0)
  val favoritesCount: StateFlow<Int> = _favoritesCount.asStateFlow()

  private val _isLocating = MutableStateFlow(false)
  val isLocating: StateFlow<Boolean> = _isLocating.asStateFlow()

  private val _event = Channel<SearchEvent>(
    capacity = RENDEZVOUS,
    onBufferOverflow = DROP_LATEST
  )
  val event: Flow<SearchEvent> = _event.receiveAsFlow()

  private var mode: SearchMode = SearchMode.Picker
  private var lastLocations: List<Location> = emptyList()
  private var lastRecents: List<Location> = emptyList()
  private var favorites: List<LocationFavorite> = emptyList()
  private val favoriteLocationIds: Set<Long>
    get() = favorites.mapTo(mutableSetOf()) { it.location.id }

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    onBackgroundError(throwable)
  }

  init {
    observeFavorites()
    loadRecentLocations()
    observeQueryChanges()
  }

  fun dispatch(action: SearchAction) {
    when (action) {
      is BackClick -> onBackClick()
      is HeartClick -> onHeartClick(action.id)
      is LocationSelect -> onLocationSelect(action.id)
      is PermissionResult -> onPermissionResult(action)
      is QueryChange -> onQueryChange(action.query)
      is Retry -> onRetry()
      is SetMode -> onSetMode(action.mode)
      is UseMyLocationClick -> onUseMyLocationClick()
    }
  }

  private fun onUseMyLocationClick() {
    send(RequestLocationPermission)
  }

  private fun onPermissionResult(action: PermissionResult) {
    when {
      action.granted -> fetchCurrentLocation()
      action.canAskAgain -> Unit
      else -> send(OpenAppSettings)
    }
  }

  private fun fetchCurrentLocation() {
    _isLocating.update { true }
    useCases.obtainCurrentLocation()
      .onEach(::onCurrentLocationResult)
      .launchIn(viewModelScope)
  }

  private fun onCurrentLocationResult(result: Result<Location>) {
    result.fold(
      onSuccess = ::onCurrentLocationResolved,
      onFailure = { onCurrentLocationError() }
    )
  }

  private fun onCurrentLocationResolved(location: Location) {
    viewModelScope.launch(errorHandler) {
      useCases.saveRecentLocation(location)
      _isLocating.update { false }
      send(NavigateBackWithResult(location))
    }
  }

  private fun onCurrentLocationError() {
    _isLocating.update { false }
    showError()
  }

  private fun onSetMode(nextMode: SearchMode) {
    mode = nextMode
  }

  private fun onBackClick() {
    send(NavigateBack)
  }

  private fun onLocationSelect(id: Long) {
    when (mode) {
      SearchMode.Picker -> onPickLocation(id = id)
      SearchMode.Favorites -> onHeartClick(id = id)
    }
  }

  private fun onPickLocation(id: Long) {
    val location = findLocation(id) ?: return
    viewModelScope.launch(errorHandler) {
      useCases.saveRecentLocation(location)
      send(NavigateBackWithResult(location))
    }
  }

  private fun onHeartClick(id: Long) {
    val location = findLocation(id = id) ?: return
    val existing = findFavoriteByLocationId(locationId = id)
    if (existing == null && _favoritesCount.value >= MAX_FAVORITES) {
      send(LimitReached)
      return
    }
    viewModelScope.launch(errorHandler) {
      toggleFavorite(location = location, existing = existing)
    }
  }

  private fun findFavoriteByLocationId(locationId: Long): LocationFavorite? =
    favorites.firstOrNull { it.location.id == locationId }

  private suspend fun toggleFavorite(location: Location, existing: LocationFavorite?) {
    when (existing) {
      null -> useCases.addFavorite(location = location)
      else -> useCases.removeFavorite(id = existing.id)
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

  private fun observeFavorites() {
    useCases.observeFavorites()
      .onEach(::onFavoritesResult)
      .launchIn(viewModelScope)
  }

  private fun onFavoritesResult(result: Result<List<LocationFavorite>>) {
    result.fold(
      onSuccess = ::onFavoritesChanged,
      onFailure = { showError() }
    )
  }

  private fun onFavoritesChanged(next: List<LocationFavorite>) {
    favorites = next
    _favoritesCount.update { next.size }
    rebuildListsWithLatestFavorites()
  }

  private fun rebuildListsWithLatestFavorites() {
    _state.update { current ->
      stateFactory.refreshFavorites(
        current = current,
        recents = lastRecents,
        lastResults = lastLocations,
        favoriteLocationIds = favoriteLocationIds
      )
    }
  }

  private fun loadRecentLocations() {
    useCases.getRecentLocations()
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
    lastRecents = locations
    lastLocations = locations
    _state.update { current ->
      stateFactory.recentsStateOrIdle(
        query = current.query,
        locations = locations,
        favoriteLocationIds = favoriteLocationIds
      )
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
    _state.update {
      stateFactory.resultsStateOrEmpty(
        query = query,
        locations = locations,
        favoriteLocationIds = favoriteLocationIds
      )
    }
  }

  private fun onBackgroundError(throwable: Throwable) {
    when (throwable) {
      is LocationFavoritesLimitReached -> send(LimitReached)
      else -> showError()
    }
  }

  private fun showError() {
    _state.update { current ->
      stateFactory.errorState(query = current.query, message = resources.defaultError())
    }
  }

  private fun currentQuery(): String =
    _state.value.query

  private fun findLocation(id: Long): Location? =
    lastLocations.firstOrNull { it.id == id }

  private fun String.canBeSearched(): Boolean =
    length >= MIN_QUERY_LENGTH

  private fun send(event: SearchEvent) {
    _event.trySend(event)
  }

  private companion object {
    const val MIN_QUERY_LENGTH = 2
    const val SEARCH_DEBOUNCE_MS = 400L
  }
}
