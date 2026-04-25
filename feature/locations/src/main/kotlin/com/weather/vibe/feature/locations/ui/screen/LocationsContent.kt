package com.weather.vibe.feature.locations.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.locations.presentation.LocationsAction
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddLocationClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ExitCompareMode
import com.weather.vibe.feature.locations.presentation.LocationsAction.OpenLocationDetails
import com.weather.vibe.feature.locations.presentation.LocationsAction.PullToRefresh
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameLocationFavoriteClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ReorderLocationFavorites
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.LocationsDefaults.CompareMinCards
import com.weather.vibe.feature.locations.ui.LocationsDefaults.SnackbarPushOffset
import com.weather.vibe.feature.locations.ui.LocationsKeys.EMPTY
import com.weather.vibe.feature.locations.ui.LocationsKeys.card
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareHintPickOne
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareHintPickZero
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.headerSubtitle
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetTitleRename
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.screenTitle
import com.weather.vibe.feature.locations.ui.component.add.AddLocationFab
import com.weather.vibe.feature.locations.ui.component.compare.LocationCompareSheet
import com.weather.vibe.feature.locations.ui.component.empty.LocationsEmptyState
import com.weather.vibe.feature.locations.ui.component.header.CompareTogglePill
import com.weather.vibe.feature.locations.ui.component.label.LocationFavoriteLabelSheet
import com.weather.vibe.feature.locations.ui.component.row.LocationRow
import com.weather.vibe.feature.locations.ui.reorder.LocationsReorderAutoScroller
import com.weather.vibe.feature.locations.ui.reorder.rememberLocationsReorderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationsContent(
  modifier: Modifier = Modifier,
  state: LocationsUiState,
  snackbarHostState: SnackbarHostState,
  dispatch: (LocationsAction) -> Unit
) {

  var renameTarget by remember { mutableStateOf<LocationCardUiState?>(null) }

  val snackbarVisible by remember(snackbarHostState) {
    derivedStateOf { snackbarHostState.currentSnackbarData != null }
  }

  val fabLift by animateDpAsState(
    targetValue = if (snackbarVisible) SnackbarPushOffset else 0.dp,
    label = "fab_snackbar_lift"
  )

  VibeScreenScaffold(
    modifier = modifier,
    header = {
      VibeScreenHeader(
        title = screenTitle(),
        subtitle = locationsSubtitle(state),
        trailing = {
          if (state is Loaded && state.cards.size >= CompareMinCards) {
            CompareTogglePill(
              compareMode = state.compareMode,
              onClick = { dispatch(ToggleCompareMode) }
            )
          }
        }
      )
    }
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      LocationsBody(
        state = state,
        onRenameRequest = { renameTarget = it },
        dispatch = dispatch
      )
      AddLocationFab(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(
            end = Medium,
            bottom = LocationsDefaults.FabBottomOffset + fabLift
          ),
        enabled = state !is Loaded || state.canAddMoreFavorites,
        onClick = { dispatch(AddLocationClick) }
      )
      SnackbarHost(
        modifier = Modifier.align(Alignment.BottomCenter),
        hostState = snackbarHostState
      )
    }
  }
  renameTarget?.let { card ->
    LocationFavoriteLabelSheet(
      title = labelSheetTitleRename(),
      locationName = card.name,
      initialLabel = card.label,
      onDismiss = { renameTarget = null },
      onSubmit = { newLabel ->
        dispatch(
          RenameLocationFavoriteClick(
            favoriteId = card.favoriteId,
            label = newLabel
          )
        )
        renameTarget = null
      }
    )
  }
}

@Composable
private fun locationsSubtitle(state: LocationsUiState): String? = when (state) {
  is Loaded -> when {
    state.compareMode && state.selectedIds.isEmpty() -> compareHintPickZero()
    state.compareMode && state.selectedIds.size == 1 -> compareHintPickOne()
    else -> headerSubtitle(count = state.cards.size, limit = MAX_FAVORITES)
  }
  else -> null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsBody(
  modifier: Modifier = Modifier,
  state: LocationsUiState,
  onRenameRequest: (LocationCardUiState) -> Unit,
  dispatch: (LocationsAction) -> Unit
) {
  when (state) {
    is Loading -> LocationsLoading(modifier = modifier)
    is Error -> LocationsError(modifier = modifier, message = state.message)
    is Loaded -> LocationsLoaded(
      modifier = modifier,
      state = state,
      onRenameRequest = onRenameRequest,
      dispatch = dispatch
    )
  }
}

@Composable
private fun LocationsLoading(modifier: Modifier = Modifier) {
  LoadingIndicator(modifier = modifier.fillMaxSize())
}

@Composable
private fun LocationsError(modifier: Modifier = Modifier, message: String) {
  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    VibeMessage(title = message, message = "")
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsLoaded(
  modifier: Modifier = Modifier,
  state: Loaded,
  onRenameRequest: (LocationCardUiState) -> Unit,
  dispatch: (LocationsAction) -> Unit
) {
  PullToRefreshBox(
    modifier = modifier.fillMaxSize(),
    isRefreshing = state.isRefreshing,
    onRefresh = { dispatch(PullToRefresh) }
  ) {
    LocationsList(
      state = state,
      onRenameRequest = onRenameRequest,
      dispatch = dispatch
    )
  }
  state.comparePair?.let { pair ->
    LocationCompareSheet(
      pair = pair,
      onDismiss = { dispatch(ExitCompareMode) }
    )
  }
}

@Composable
private fun LocationsList(
  state: Loaded,
  onRenameRequest: (LocationCardUiState) -> Unit,
  dispatch: (LocationsAction) -> Unit
) {
  val listState = rememberLazyListState()
  val reorderState = rememberLocationsReorderState(
    listState = listState,
    cards = state.cards,
    onCommit = { orderedIds -> dispatch(ReorderLocationFavorites(orderedIds = orderedIds)) }
  )
  val isEmpty by remember(reorderState) {
    derivedStateOf { reorderState.orderedCards.isEmpty() }
  }

  LocationsReorderAutoScroller(
    listState = listState,
    reorder = reorderState,
    edgeZone = LocationsDefaults.ReorderEdgeZone,
    pixelsPerSecond = LocationsDefaults.ReorderAutoScrollSpeed
  )

  LazyColumn(
    state = listState,
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(
      start = Medium,
      end = Medium,
      top = Medium,
      bottom = LocationsDefaults.ListBottomPadding
    ),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    if (isEmpty) {
      item(key = EMPTY) { LocationsEmptyState() }
      return@LazyColumn
    }
    items(
      items = reorderState.orderedCards,
      key = { card -> card(card.favoriteId) }
    ) { card ->
      LocationRow(
        card = card,
        compareMode = state.compareMode,
        isSelected = state.selectedIds.contains(card.favoriteId),
        isLocked = state.isCardLocked(card.favoriteId),
        reorder = reorderState,
        onClick = { dispatch(OpenLocationDetails(favoriteId = card.favoriteId)) },
        onRename = { onRenameRequest(card) },
        onDelete = { dispatch(RemoveLocationFavoriteClick(favoriteId = card.favoriteId)) }
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationsContent(
      state = LocationsPreviewData.browseLoaded,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}
