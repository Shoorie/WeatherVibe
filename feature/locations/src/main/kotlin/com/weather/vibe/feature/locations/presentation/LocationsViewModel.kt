package com.weather.vibe.feature.locations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.error.LocationFavoritesLimitReached
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
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
import com.weather.vibe.feature.locations.presentation.factory.LocationsFactories
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LocationsViewModel(
  private val factories: LocationsFactories,
  private val useCases: LocationsUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<LocationsUiState>(Loading)
  val state: StateFlow<LocationsUiState> = _state.asStateFlow()

  private val _event = Channel<LocationsEvent>(BUFFERED)
  val event: Flow<LocationsEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, _ -> }

  private var latestFavorites: List<LocationFavoriteWithWeather> = emptyList()
  private var latestTemperatureUnit: TemperatureUnit = CELSIUS
  private var observeJob: Job? = null

  fun dispatch(action: LocationsAction) {
    when (action) {
      is AddLocationClick -> onAddLocationClick()
      is ExitCompareMode -> onExitCompareMode()
      is Initialize -> onInitialize()
      is OpenLocationDetails -> onOpenLocationDetails(action.favoriteId)
      is PullToRefresh -> onPullToRefresh()
      is RemoveLocationFavoriteClick -> onRemoveFavoriteClick(action.favoriteId)
      is RenameLocationFavoriteClick -> onRenameFavoriteClick(action.favoriteId, action.label)
      is ToggleCompareMode -> onToggleCompareMode()
      is UndoRemoveLocationFavoriteClick -> onUndoRemoveFavoriteClick(action)
    }
  }

  private fun onInitialize() {
    if (observeJob?.isActive == true) return
    observeJob = combine(
      useCases.observeFavoritesWithWeather(),
      useCases.observeTemperatureUnit(),
      ::onFavoritesUpdate
    ).launchIn(viewModelScope)
  }

  private fun onFavoritesUpdate(
    favoritesResult: Result<List<LocationFavoriteWithWeather>>,
    temperatureUnit: TemperatureUnit
  ) {
    favoritesResult.fold(
      onSuccess = { sources -> onFavoritesLoaded(sources, temperatureUnit) },
      onFailure = ::onFavoritesLoadFailed
    )
  }

  private fun onFavoritesLoaded(
    sources: List<LocationFavoriteWithWeather>,
    temperatureUnit: TemperatureUnit
  ) {

    latestFavorites = sources
    latestTemperatureUnit = temperatureUnit

    _state.update { current ->
      factories.loaded.create(
        current = current,
        sources = sources,
        temperatureUnit = temperatureUnit
      )
    }
  }

  private fun onFavoritesLoadFailed(throwable: Throwable) {
    _state.update { factories.state.error(throwable) }
  }

  private fun onPullToRefresh() {
    markRefreshing(true)
    viewModelScope.launch(errorHandler) {
      try {
        useCases.refreshFavoritesWeather()
      } finally {
        markRefreshing(false)
      }
    }
  }

  private fun onToggleCompareMode() =
    updateLoaded { current ->
      if (current.hasEnoughCardsForCompare) {
        current.withToggledCompareMode()
      } else current
    }

  private fun onOpenLocationDetails(favoriteId: Long) =
    updateLoaded { current ->
      if (!current.compareMode) return@updateLoaded current
      factories.loaded.afterSelectionChange(
        current = current.withToggledSelection(favoriteId),
        sources = latestFavorites,
        temperatureUnit = latestTemperatureUnit
      )
    }

  private fun onExitCompareMode() {
    updateLoaded(Loaded::withSelectionCleared)
  }

  private fun onAddLocationClick() {
    send(event = NavigateToSearch)
  }

  private fun onRemoveFavoriteClick(favoriteId: Long) {

    val source = latestFavorites
      .firstOrNull { it.favorite.id == favoriteId }
      ?: return

    viewModelScope.launch(errorHandler) {
      useCases.removeFavorite(favoriteId)
      send(removedSnackbarFor(source))
    }
  }

  private fun onUndoRemoveFavoriteClick(action: UndoRemoveLocationFavoriteClick) {
    viewModelScope.launch(errorHandler) {
      try {
        useCases.addFavorite(action.location, action.label)
      } catch (_: LocationFavoritesLimitReached) {
        send(event = ShowLimitReachedSnackbar)
      }
    }
  }

  private fun onRenameFavoriteClick(favoriteId: Long, label: String?) {
    viewModelScope.launch(errorHandler) {
      useCases.renameFavorite(favoriteId, label)
    }
  }

  private fun removedSnackbarFor(source: LocationFavoriteWithWeather): ShowRemovedSnackbar =
    ShowRemovedSnackbar(
      locationName = source.favorite.location.name,
      location = source.favorite.location,
      label = source.favorite.label
    )

  private fun markRefreshing(isRefreshing: Boolean) =
    updateLoaded { current ->
      current.withRefreshing(isRefreshing)
    }

  private fun updateLoaded(transform: (Loaded) -> Loaded) {
    _state.update { current ->
      if (current is Loaded) transform(current) else current
    }
  }

  private fun send(event: LocationsEvent) {
    viewModelScope.launch(errorHandler) {
      _event.send(event)
    }
  }
}
