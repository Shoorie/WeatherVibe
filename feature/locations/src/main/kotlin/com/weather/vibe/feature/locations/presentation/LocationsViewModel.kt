package com.weather.vibe.feature.locations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddLocationClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ExitCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.Initialize
import com.weather.vibe.feature.locations.presentation.LocationsAction.OpenLocationDetails
import com.weather.vibe.feature.locations.presentation.LocationsAction.PullToRefresh
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.UndoRemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowLimitReachedSnackbar
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowRemovedSnackbar
import com.weather.vibe.feature.locations.presentation.factory.LocationComparePairBuilder
import com.weather.vibe.feature.locations.presentation.factory.LocationsFactories
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import com.weather.vibe.feature.locations.presentation.state.hasEnoughCardsForCompare
import com.weather.vibe.feature.locations.presentation.state.withCards
import com.weather.vibe.feature.locations.presentation.state.withComparePair
import com.weather.vibe.feature.locations.presentation.state.withRefreshing
import com.weather.vibe.feature.locations.presentation.state.withSelectionCleared
import com.weather.vibe.feature.locations.presentation.state.withToggledCompareMode
import com.weather.vibe.feature.locations.presentation.state.withToggledSelection
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LocationsViewModel(
  private val factories: LocationsFactories,
  private val useCases: LocationsUseCases,
  private val comparePairBuilder: LocationComparePairBuilder
) : ViewModel() {

  private val _state = MutableStateFlow<LocationsUiState>(Loading)
  val state: StateFlow<LocationsUiState> = _state.asStateFlow()

  private val _event = Channel<LocationsEvent>(capacity = BUFFERED)
  val event: Flow<LocationsEvent> = _event.receiveAsFlow()

  private var latestFavorites: List<LocationFavoriteWithWeather> = emptyList()
  private var latestTemperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS
  private var observeJob: Job? = null

  fun dispatch(action: LocationsAction) {
    when (action) {
      is AddLocationClick -> onAddLocationClick()
      is OpenLocationDetails -> onOpenLocationDetails(action.favoriteId)
      is ExitCompareMode -> onExitCompareMode()
      is Initialize -> onInitialize()
      is PullToRefresh -> onPullToRefresh()
      is RemoveLocationFavoriteClick -> onRemoveFavoriteClick(action.favoriteId)
      is RenameLocationFavoriteClick -> onRenameFavoriteClick(
        action.favoriteId,
        action.label
      )

      is ToggleCompareMode -> onToggleCompareMode()
      is UndoRemoveLocationFavoriteClick -> onUndoRemoveFavoriteClick(action)
    }
  }

  private fun onInitialize() {
    if (observeJob?.isActive == true) return
    observeJob = combine(
      useCases.observeFavoritesWithWeather(),
      useCases.observeTemperatureUnit()
    ) { favoritesResult, temperatureUnit -> favoritesResult to temperatureUnit }
      .onEach { (favoritesResult, temperatureUnit) ->
        favoritesResult.fold(
          { sources -> onFavoritesLoaded(sources = sources, temperatureUnit = temperatureUnit) },
          ::onFavoritesLoadFailed
        )
      }
      .launchIn(viewModelScope)
  }

  private fun onFavoritesLoaded(
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ) {
    latestFavorites = sources
    latestTemperatureUnit = temperatureUnit
    val cards = factories.state.mapCards(sources = sources, temperatureUnit = temperatureUnit)
    _state.update { current ->
      val next = when (current) {
        is Loaded -> current.withCards(cards = cards)
        is Loading, is Error -> factories.state.loadedWith(cards = cards)
      }
      next.withComparePair(pair = rebuildComparePair(loaded = next))
    }
  }

  private fun onFavoritesLoadFailed(throwable: Throwable) {
    _state.update { factories.state.error(throwable = throwable) }
  }

  private fun onPullToRefresh() {
    markRefreshing(isRefreshing = true)
    viewModelScope.launch {
      try {
        useCases.refreshFavoritesWeather()
      } finally {
        markRefreshing(isRefreshing = false)
      }
    }
  }

  private fun markRefreshing(isRefreshing: Boolean) {
    _state.update { current ->
      if (current is Loaded) current.withRefreshing(isRefreshing = isRefreshing) else current
    }
  }

  private fun onToggleCompareMode() {
    _state.update { current ->
      if (current !is Loaded || !current.hasEnoughCardsForCompare) return@update current
      current.withToggledCompareMode()
    }
  }

  private fun onOpenLocationDetails(favoriteId: Long) {
    _state.update { current ->
      if (current !is Loaded || !current.compareMode) return@update current
      val afterToggle = current.withToggledSelection(favoriteId = favoriteId)
      afterToggle.withComparePair(pair = rebuildComparePair(loaded = afterToggle))
    }
  }

  private fun onExitCompareMode() {
    _state.update { current ->
      if (current !is Loaded) return@update current
      current.withSelectionCleared()
    }
  }

  private fun onAddLocationClick() {
    send(NavigateToSearch)
  }

  private fun onRemoveFavoriteClick(favoriteId: Long) {
    val source = latestFavorites.firstOrNull { it.favorite.id == favoriteId } ?: return
    viewModelScope.launch {
      useCases.removeFavorite(id = favoriteId)
      send(
        ShowRemovedSnackbar(
          locationName = source.favorite.location.name,
          location = source.favorite.location,
          label = source.favorite.label
        )
      )
    }
  }

  private fun onUndoRemoveFavoriteClick(action: UndoRemoveLocationFavoriteClick) {
    viewModelScope.launch {
      try {
        useCases.addFavorite(location = action.location, label = action.label)
      } catch (limitReached: LocationFavoritesLimitReached) {
        send(ShowLimitReachedSnackbar)
      }
    }
  }

  private fun onRenameFavoriteClick(favoriteId: Long, label: String?) {
    viewModelScope.launch {
      useCases.renameFavorite(id = favoriteId, label = label)
    }
  }

  private fun rebuildComparePair(loaded: Loaded) =
    comparePairBuilder.createFor(
      cards = loaded.cards,
      selectedFavoriteIds = loaded.selectedFavoriteIds,
      sources = latestFavorites,
      temperatureUnit = latestTemperatureUnit
    )

  private fun send(event: LocationsEvent) {
    viewModelScope.launch { _event.send(event) }
  }
}
