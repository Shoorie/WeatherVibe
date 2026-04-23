package com.weather.vibe.feature.locations.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.components.message.VibeMessage
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.locations.preview.LocationsPreviewData
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.labelSheetTitleRename
import com.weather.vibe.feature.locations.presentation.LocationsAction
import com.weather.vibe.feature.locations.presentation.LocationsAction.AddCityClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CardClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.CloseCompare
import com.weather.vibe.feature.locations.presentation.LocationsAction.RefreshClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RemoveClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.RenameClick
import com.weather.vibe.feature.locations.presentation.LocationsAction.ToggleCompareMode
import com.weather.vibe.domain.location.usecase.AddFavorite
import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Error
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loading
import com.weather.vibe.feature.locations.ui.LocationsDefaults
import com.weather.vibe.feature.locations.ui.component.add.AddLocationFab
import com.weather.vibe.feature.locations.ui.component.compare.LocationCompareSheet
import com.weather.vibe.feature.locations.ui.component.empty.LocationsEmptyState
import com.weather.vibe.feature.locations.ui.component.header.LocationsHeader
import com.weather.vibe.feature.locations.ui.component.label.FavoriteLabelSheet
import com.weather.vibe.feature.locations.ui.component.row.LocationRow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationsContent(
  modifier: Modifier = Modifier,
  state: LocationsUiState,
  snackbarHostState: SnackbarHostState,
  dispatch: (LocationsAction) -> Unit
) {
  var renameTarget by remember { mutableStateOf<LocationCardUi?>(null) }
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(brush = screenBackground())
  ) {
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
          bottom = LocationsDefaults.FabBottomOffset
        ),
      enabled = canAddMoreFavorites(state = state),
      onClick = { dispatch(AddCityClick) }
    )
    SnackbarHost(
      modifier = Modifier.align(Alignment.BottomCenter),
      hostState = snackbarHostState
    )
  }
  renameTarget?.let { card ->
    FavoriteLabelSheet(
      title = labelSheetTitleRename(),
      locationName = card.name,
      initialLabel = card.label,
      onDismiss = { renameTarget = null },
      onSubmit = { newLabel ->
        dispatch(RenameClick(cardId = card.favoriteId.toString(), label = newLabel))
        renameTarget = null
      }
    )
  }
}

private fun canAddMoreFavorites(state: LocationsUiState): Boolean =
  state !is Loaded || state.cards.size < AddFavorite.FAVORITES_LIMIT

@Composable
private fun screenBackground(): Brush {
  val gradientStart = colors.backgroundGradientStart
  val gradientEnd = colors.backgroundGradientEnd
  return remember(gradientStart, gradientEnd) {
    Brush.verticalGradient(listOf(gradientStart, gradientEnd))
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsBody(
  modifier: Modifier = Modifier,
  state: LocationsUiState,
  onRenameRequest: (LocationCardUi) -> Unit,
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
private fun LocationsLoading(modifier: Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding(),
    contentAlignment = Alignment.Center
  ) {
    LoadingIndicator()
  }
}

@Composable
private fun LocationsError(modifier: Modifier, message: String) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .padding(Medium),
    contentAlignment = Alignment.Center
  ) {
    VibeMessage(message = message)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationsLoaded(
  modifier: Modifier = Modifier,
  state: Loaded,
  onRenameRequest: (LocationCardUi) -> Unit,
  dispatch: (LocationsAction) -> Unit
) {
  PullToRefreshBox(
    modifier = modifier.fillMaxSize(),
    isRefreshing = state.isRefreshing,
    onRefresh = { dispatch(RefreshClick) }
  ) {
    LocationsList(
      cards = state.cards,
      compareMode = state.compareMode,
      selectedIds = state.selectedIds,
      isRefreshing = state.isRefreshing,
      onRenameRequest = onRenameRequest,
      dispatch = dispatch
    )
  }
  state.comparePair?.let { pair ->
    LocationCompareSheet(
      pair = pair,
      onDismiss = { dispatch(CloseCompare) }
    )
  }
}

@Composable
private fun LocationsList(
  cards: ImmutableList<LocationCardUi>,
  compareMode: Boolean,
  selectedIds: ImmutableSet<String>,
  isRefreshing: Boolean,
  onRenameRequest: (LocationCardUi) -> Unit,
  dispatch: (LocationsAction) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding(),
    contentPadding = PaddingValues(
      start = Medium,
      end = Medium,
      top = Medium,
      bottom = ExtraLarge
    ),
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    item {
      LocationsHeader(
        modifier = Modifier.padding(bottom = Small),
        count = cards.size,
        compareMode = compareMode,
        selectedCount = selectedIds.size,
        isRefreshing = isRefreshing,
        onToggleCompareMode = { dispatch(ToggleCompareMode) },
        onRefreshClick = { dispatch(RefreshClick) }
      )
    }
    if (cards.isEmpty()) {
      item { LocationsEmptyState() }
      return@LazyColumn
    }
    itemsIndexed(items = cards, key = { _, card -> card.favoriteId }) { index, card ->
      val isSelected = selectedIds.contains(card.favoriteId.toString())
      val isLocked = compareMode &&
        selectedIds.size >= LocationsDefaults.SelectionLimit &&
        !isSelected
      LocationRow(
        card = card,
        positionIndex = index,
        compareMode = compareMode,
        isSelected = isSelected,
        isLocked = isLocked,
        onClick = { dispatch(CardClick(cardId = card.favoriteId.toString())) },
        onRename = { onRenameRequest(card) },
        onDelete = { dispatch(RemoveClick(cardId = card.favoriteId.toString())) }
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun PreviewBrowse() {
  WeatherVibeTheme {
    LocationsContent(
      state = LocationsPreviewData.browseLoaded,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewComparing() {
  WeatherVibeTheme {
    LocationsContent(
      state = LocationsPreviewData.comparingLoaded,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
  WeatherVibeTheme {
    LocationsContent(
      state = Loading,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}
