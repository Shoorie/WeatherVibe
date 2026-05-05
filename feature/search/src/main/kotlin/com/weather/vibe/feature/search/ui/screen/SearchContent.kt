package com.weather.vibe.feature.search.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.header.VibeScreenHeader
import com.weather.vibe.core.designsystem.components.header.VibeScreenScaffold
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.domain.location.policy.LocationFavoritesPolicy.MAX_FAVORITES
import com.weather.vibe.feature.search.presentation.SearchAction
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchMode
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.preview.SearchPreview
import com.weather.vibe.feature.search.ui.SearchResources.Texts.favoritesCapacity
import com.weather.vibe.feature.search.ui.SearchResources.Texts.favoritesCapacityFull
import com.weather.vibe.feature.search.ui.SearchResources.Texts.screenSubtitle
import com.weather.vibe.feature.search.ui.SearchResources.Texts.screenTitle
import com.weather.vibe.feature.search.ui.component.banner.LocationFavoritesCapacityBanner
import com.weather.vibe.feature.search.ui.component.bar.SearchField
import com.weather.vibe.feature.search.ui.component.list.RecentsSection
import com.weather.vibe.feature.search.ui.component.list.ResultsSection
import com.weather.vibe.feature.search.ui.component.state.SearchEmptyState
import com.weather.vibe.feature.search.ui.component.state.SearchErrorState
import com.weather.vibe.feature.search.ui.component.state.SearchIdleState
import com.weather.vibe.feature.search.ui.component.state.SearchLoadingState

@Composable
internal fun SearchContent(
  modifier: Modifier = Modifier,
  state: SearchUiState,
  mode: SearchMode,
  favoritesCount: Int,
  snackbarHostState: SnackbarHostState,
  dispatch: (SearchAction) -> Unit
) {
  VibeScreenScaffold(
    modifier = modifier.background(rememberAppBackgroundBrush()),
    header = {
      VibeScreenHeader(
        title = screenTitle(),
        subtitle = screenSubtitle(),
        onBackClicked = { dispatch(BackClick) }
      )
    }
  ) {
    val callbacks = remember(dispatch) { SearchCallbacks(dispatch) }
    Box(modifier = Modifier.fillMaxSize()) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = Padding.Medium),
        verticalArrangement = Arrangement.spacedBy(Padding.Medium)
      ) {
        SearchField(
          query = state.query,
          onQueryChange = callbacks.onQueryChange
        )
        if (mode == SearchMode.Favorites) {
          CapacityBanner(used = favoritesCount)
        }
        SearchStateContent(
          state = state,
          showHeart = mode == SearchMode.Favorites,
          onLocationClick = callbacks.onLocationSelect,
          onHeartClick = callbacks.onHeartClick,
          onRetryClick = callbacks.onRetry
        )
      }
      SnackbarHost(
        modifier = Modifier.align(Alignment.BottomCenter),
        hostState = snackbarHostState
      )
    }
  }
}

@Composable
private fun CapacityBanner(used: Int) {

  val limit = MAX_FAVORITES
  val isFull = used >= limit
  val label = if (isFull) favoritesCapacityFull(limit = limit)
  else favoritesCapacity(used = used, limit = limit)

  LocationFavoritesCapacityBanner(
    label = label,
    accentColor = if (isFull) colors.error else colors.accent,
    labelColor = if (isFull) colors.error else colors.onSurfaceVariant
  )
}

@Composable
private fun SearchStateContent(
  modifier: Modifier = Modifier,
  state: SearchUiState,
  showHeart: Boolean,
  onLocationClick: (Long) -> Unit,
  onHeartClick: (Long) -> Unit,
  onRetryClick: () -> Unit
) {
  when (state) {
    is Idle -> SearchIdleState(modifier)
    is Searching -> SearchLoadingState(modifier)
    is Recents -> RecentsSection(
      modifier = modifier,
      locations = state.locations,
      showHeart = showHeart,
      onLocationClick = onLocationClick,
      onHeartClick = onHeartClick
    )

    is Results -> ResultsSection(
      modifier = modifier,
      locations = state.locations,
      showHeart = showHeart,
      onLocationClick = onLocationClick,
      onHeartClick = onHeartClick
    )

    is Empty -> SearchEmptyState(
      modifier = modifier,
      query = state.query
    )

    is Error -> SearchErrorState(
      modifier = modifier,
      message = state.message,
      onRetry = onRetryClick
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchPreview::class)
  state: SearchUiState
) {
  WeatherVibeTheme {
    SearchContent(
      state = state,
      mode = SearchMode.Favorites,
      favoritesCount = 3,
      snackbarHostState = remember { SnackbarHostState() },
      dispatch = {}
    )
  }
}
