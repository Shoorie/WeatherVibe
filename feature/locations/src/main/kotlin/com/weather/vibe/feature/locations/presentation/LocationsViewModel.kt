package com.weather.vibe.feature.locations.presentation

import android.util.Log
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
import com.weather.vibe.feature.locations.presentation.LocationsAction.ReorderLocationFavorites
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.UndoRemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowErrorSnackbar
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

  private val snackbarErrorHandler = CoroutineExceptionHandler { _, error ->
    emit(snackbarFor(error = error))
  }

  private val backgroundErrorHandler = CoroutineExceptionHandler { _, error ->
    Log.w(TAG, "Background work failed", error)
  }

  private var latestFavorites: List<LocationFavoriteWithWeather> = emptyList()
  private var latestTemperatureUnit: TemperatureUnit = CELSIUS
  private var observeJob: Job? = null
  private var reorderJob: Job? = null
  private var pendingRemoval: PendingRemoval? = null

  fun dispatch(action: LocationsAction) {
    when (action) {
      is AddLocationClick -> onAddLocationClick()
      is ExitCompareMode -> onExitCompareMode()
      is Initialize -> onInitialize()
      is OpenLocationDetails -> onOpenLocationDetails(action.favoriteId)
      is PullToRefresh -> onPullToRefresh()
      is RemoveLocationFavoriteClick -> onRemoveFavoriteClick(action.favoriteId)
      is RenameLocationFavoriteClick -> onRenameFavoriteClick(action.favoriteId, action.label)
      is ReorderLocationFavorites -> onReorderFavorites(action.orderedIds)
      is ToggleCompareMode -> onToggleCompareMode()
      is UndoRemoveLocationFavoriteClick -> onUndoRemoveFavoriteClick()
    }
  }

  private fun onInitialize() {
    if (observeJob?.isActive == true) return
    observeJob = combine(
      useCases.observeFavoritesWithWeather(),
      useCases.observeTemperatureUnit(),
      ::onFavoritesUpdate
    ).launchIn(viewModelScope)
    refreshStaleInBackground()
  }

  private fun refreshStaleInBackground() {
    viewModelScope.launch(backgroundErrorHandler) {
      useCases.refreshStaleFavoritesWeather()
    }
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
    viewModelScope.launch(snackbarErrorHandler) {
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
    emit(NavigateToSearch)
  }

  private fun onRemoveFavoriteClick(favoriteId: Long) {
    val source = latestFavorites.firstOrNull { it.favorite.id == favoriteId } ?: return
    val originalOrder = latestFavorites.map { it.favorite.id }
    viewModelScope.launch(snackbarErrorHandler) {
      useCases.removeFavorite(favoriteId)
      pendingRemoval = PendingRemoval(
        location = source.favorite.location,
        label = source.favorite.label,
        snapshot = source.snapshot,
        removedFavoriteId = favoriteId,
        originalOrder = originalOrder
      )
      emit(ShowRemovedSnackbar(locationName = source.favorite.location.name))
    }
  }

  private fun onUndoRemoveFavoriteClick() {
    val pending = pendingRemoval ?: return
    pendingRemoval = null
    viewModelScope.launch(snackbarErrorHandler) {
      useCases.restoreFavoriteAtOriginalPosition(
        location = pending.location,
        label = pending.label,
        snapshot = pending.snapshot,
        removedFavoriteId = pending.removedFavoriteId,
        originalOrder = pending.originalOrder
      )
    }
  }

  private fun onRenameFavoriteClick(favoriteId: Long, label: String?) {
    viewModelScope.launch(snackbarErrorHandler) {
      useCases.renameFavorite(favoriteId, label)
    }
  }

  private fun onReorderFavorites(orderedIds: List<Long>) {
    reorderJob?.cancel()
    reorderJob = viewModelScope.launch(snackbarErrorHandler) {
      useCases.reorderFavorites(orderedIds = orderedIds)
    }
  }

  private fun markRefreshing(isRefreshing: Boolean) =
    updateLoaded { current ->
      current.withRefreshing(isRefreshing)
    }

  private fun updateLoaded(transform: (Loaded) -> Loaded) {
    _state.update { current ->
      if (current is Loaded) transform(current) else current
    }
  }

  private fun emit(event: LocationsEvent) {
    _event.trySend(event)
  }

  private fun snackbarFor(error: Throwable): LocationsEvent = when (error) {
    is LocationFavoritesLimitReached -> ShowLimitReachedSnackbar
    else -> ShowErrorSnackbar
  }

  private companion object {
    const val TAG = "LocationsViewModel"
  }
}
