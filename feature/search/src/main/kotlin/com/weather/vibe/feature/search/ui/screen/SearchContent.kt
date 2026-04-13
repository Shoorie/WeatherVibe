package com.weather.vibe.feature.search.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.search.presentation.SearchAction
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchAction.Retry
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Error
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.preview.SearchPreview
import com.weather.vibe.feature.search.ui.component.bar.SearchTopBar
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
  dispatch: (SearchAction) -> Unit
) {

  val backgroundBrush = Brush.verticalGradient(
    listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(brush = backgroundBrush)
      .statusBarsPadding()
      .padding(horizontal = Padding.Medium),
    verticalArrangement = Arrangement.spacedBy(Padding.Medium)
  ) {
    SearchTopBar(
      modifier = Modifier.padding(top = Padding.Small),
      query = state.query,
      onQueryChange = { dispatch(QueryChange(it)) },
      onBack = { dispatch(BackClick) }
    )
    SearchStateContent(
      state = state,
      onLocationClick = { dispatch(LocationSelect(it)) },
      onRetryClick = { dispatch(Retry) }
    )
  }
}

@Composable
private fun SearchStateContent(
  modifier: Modifier = Modifier,
  state: SearchUiState,
  onLocationClick: (Long) -> Unit,
  onRetryClick: () -> Unit
) {
  when (state) {
    is Idle -> SearchIdleState(modifier)
    is Searching -> SearchLoadingState(modifier)
    is Recents -> RecentsSection(
      modifier = modifier,
      locations = state.locations,
      onLocationClick = onLocationClick
    )
    is Results -> ResultsSection(
      modifier = modifier,
      locations = state.locations,
      onLocationClick = onLocationClick
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
      dispatch = {}
    )
  }
}
