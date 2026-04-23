package com.weather.vibe.feature.locations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.location.error.FavoritesLimitReached
import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddCityClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CardClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CloseCompare
import com.weather.vibe.feature.locations.presentation.LocationsAction.Initialize
import com.weather.vibe.feature.locations.presentation.LocationsAction.RefreshClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.UndoRemoveClick
import com.weather.vibe.feature.locations.presentation.LocationsEvent.NavigateToSearch
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowLimitReachedSnackbar
import com.weather.vibe.feature.locations.presentation.LocationsEvent.ShowRemovedSnackbar
import com.weather.vibe.feature.locations.presentation.factory.LocationsFactories
import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import com.weather.vibe.feature.locations.presentation.state.LocationComparePair
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
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
internal class LocationsViewModel(
  private val factories: LocationsFactories,
  private val useCases: LocationsUseCases
) : ViewModel() {

  private val _state = MutableStateFlow<LocationsUiState>(Loading)
  val state: StateFlow<LocationsUiState> = _state.asStateFlow()

  private val _event = Channel<LocationsEvent>(capacity = BUFFERED)
  val event: Flow<LocationsEvent> = _event.receiveAsFlow()

  private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    onBackgroundError(throwable)
  }

  private var recentFavorites: List<FavoriteWithWeather> = emptyList()
  private var observeJob: Job? = null
  private var refreshJob: Job? = null

  fun dispatch(action: LocationsAction) {
    when (action) {
      is AddCityClick -> onAddCityClick()
      is CardClick -> onCardClick(action)
      is CloseCompare -> onCloseCompare()
      is Initialize -> onInitialize()
      is RefreshClick -> onRefreshClick()
      is RemoveClick -> onRemoveClick(action)
      is RenameClick -> onRenameClick(action)
      is ToggleCompareMode -> onToggleCompareMode()
      is UndoRemoveClick -> onUndoRemoveClick(action)
    }
  }

  private fun onInitialize() {
    if (observeJob?.isActive == true) return
    observeJob = useCases
      .observeFavoritesWithWeather()
      .onEach { result -> result.fold(::onFavoritesLoaded, ::onLoadFailed) }
      .launchIn(viewModelScope)
    refreshInBackground(forceAll = false)
  }

  private fun onFavoritesLoaded(sources: List<FavoriteWithWeather>) {
    recentFavorites = sources
    val cards = factories.state.mapCards(sources = sources)
    _state.update { current ->
      val next = when (current) {
        is Loaded -> current.copy(cards = cards)
        is Loading, is Error -> factories.state.loadedWith(cards = cards)
      }
      next.copy(comparePair = buildComparePair(cards = cards, selectedIds = next.selectedIds))
    }
    if (sources.any { it.snapshot == null }) refreshInBackground(forceAll = false)
  }

  private fun onLoadFailed(throwable: Throwable) {
    _state.update { factories.state.error(throwable = throwable) }
  }

  private fun onRefreshClick() {
    refreshInBackground(forceAll = true)
  }

  private fun refreshInBackground(forceAll: Boolean) {
    refreshJob?.cancel()
    setRefreshing(isRefreshing = true)
    refreshJob = viewModelScope.launch(errorHandler) {
      try {
        useCases.refreshFavoritesWeather(forceAll = forceAll)
      } finally {
        setRefreshing(isRefreshing = false)
      }
    }
  }

  private fun setRefreshing(isRefreshing: Boolean) {
    _state.update { current ->
      if (current is Loaded) current.copy(isRefreshing = isRefreshing) else current
    }
  }

  private fun onToggleCompareMode() {
    _state.update { current ->
      if (current !is Loaded) return@update current
      if (current.cards.size < LocationsDefaults.SelectionLimit) return@update current
      val nextCompareMode = !current.compareMode
      current.copy(
        compareMode = nextCompareMode,
        comparePair = null,
        selectedIds = persistentSetOf()
      )
    }
  }

  private fun onCardClick(action: CardClick) {
    _state.update { current ->
      if (current !is Loaded || !current.compareMode) return@update current
      val nextSelected = toggleSelection(current = current.selectedIds, cardId = action.cardId)
      current.copy(
        selectedIds = nextSelected,
        comparePair = buildComparePair(cards = current.cards, selectedIds = nextSelected)
      )
    }
  }

  private fun toggleSelection(
    current: ImmutableSet<String>,
    cardId: String
  ): ImmutableSet<String> {
    if (current.contains(cardId)) return (current - cardId).toPersistentSet()
    if (current.size >= LocationsDefaults.SelectionLimit) return current
    return (current + cardId).toPersistentSet()
  }

  private fun buildComparePair(
    cards: ImmutableList<LocationCardUi>,
    selectedIds: ImmutableSet<String>
  ): LocationComparePair? {
    if (selectedIds.size != LocationsDefaults.SelectionLimit) return null
    val selected = cards.filter { it.favoriteId.toString() in selectedIds }
    if (selected.size != LocationsDefaults.SelectionLimit) return null
    val firstSource = sourceFor(card = selected[0]) ?: return null
    val secondSource = sourceFor(card = selected[1]) ?: return null
    val firstCompareUi = factories.compare.create(card = selected[0], source = firstSource)
      ?: return null
    val secondCompareUi = factories.compare.create(card = selected[1], source = secondSource)
      ?: return null
    val winners = useCases.compareWeather(
      first = firstSource.snapshot ?: return null,
      second = secondSource.snapshot ?: return null
    )
    return factories.compare.pairOf(
      first = firstCompareUi,
      second = secondCompareUi,
      winners = winners
    )
  }

  private fun sourceFor(card: LocationCardUi): FavoriteWithWeather? =
    recentFavorites.firstOrNull { it.favorite.id == card.favoriteId }

  private fun onCloseCompare() {
    _state.update { current ->
      if (current !is Loaded) return@update current
      current.copy(selectedIds = persistentSetOf(), comparePair = null)
    }
  }

  private fun onAddCityClick() {
    send(NavigateToSearch)
  }

  private fun onRemoveClick(action: RemoveClick) {
    val id = action.cardId.toLongOrNull() ?: return
    val source = recentFavorites.firstOrNull { it.favorite.id == id } ?: return
    viewModelScope.launch(errorHandler) {
      useCases.removeFavorite(id = id)
      send(
        ShowRemovedSnackbar(
          locationName = source.favorite.location.name,
          location = source.favorite.location,
          label = source.favorite.label
        )
      )
    }
  }

  private fun onUndoRemoveClick(action: UndoRemoveClick) {
    viewModelScope.launch(errorHandler) {
      useCases.addFavorite(location = action.location, label = action.label)
    }
  }

  private fun onRenameClick(action: RenameClick) {
    val id = action.cardId.toLongOrNull() ?: return
    viewModelScope.launch(errorHandler) { useCases.renameFavorite(id = id, label = action.label) }
  }

  private fun onBackgroundError(throwable: Throwable) {
    if (throwable is FavoritesLimitReached) {
      send(ShowLimitReachedSnackbar)
      return
    }
    _state.update { current ->
      if (current is Loaded) current.copy(isRefreshing = false) else current
    }
  }

  private fun send(event: LocationsEvent) {
    viewModelScope.launch(errorHandler) { _event.send(event) }
  }
}
