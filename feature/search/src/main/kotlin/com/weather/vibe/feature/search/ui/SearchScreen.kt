package com.weather.vibe.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.presentation.SearchAction
import com.weather.vibe.feature.search.presentation.SearchAction.BackClick
import com.weather.vibe.feature.search.presentation.SearchAction.LocationSelect
import com.weather.vibe.feature.search.presentation.SearchAction.QueryChange
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBack
import com.weather.vibe.feature.search.presentation.SearchEvent.NavigateBackWithResult
import com.weather.vibe.feature.search.presentation.SearchViewModel
import com.weather.vibe.feature.search.presentation.state.LocationItemUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Empty
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Idle
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Recents
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Results
import com.weather.vibe.feature.search.presentation.state.SearchUiState.Searching
import com.weather.vibe.feature.search.preview.SearchPreview
import com.weather.vibe.feature.search.preview.params.SearchPreviewParams
import com.weather.vibe.feature.search.ui.SearchResources.Emojis.clock
import com.weather.vibe.feature.search.ui.SearchResources.Emojis.locationPin
import com.weather.vibe.feature.search.ui.SearchResources.Texts.noResultsFound
import com.weather.vibe.feature.search.ui.SearchResources.Texts.recentLocationsTitle
import com.weather.vibe.feature.search.ui.component.LocationItem
import com.weather.vibe.feature.search.ui.component.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
  onLocationSelected: (String, Double, Double) -> Unit,
  onNavigateBack: () -> Unit
) {

  val viewModel: SearchViewModel = koinViewModel()
  val query by viewModel.query.collectAsStateWithLifecycle()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val keyboardController = LocalSoftwareKeyboardController.current

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      keyboardController?.hide()
      when (event) {
        is NavigateBack -> onNavigateBack()
        is NavigateBackWithResult -> onLocationSelected(
          event.cityName,
          event.latitude,
          event.longitude
        )
      }
    }
  }

  SearchContent(
    query = query,
    state = state,
    dispatch = viewModel::dispatch
  )
}

@Composable
internal fun SearchContent(
  modifier: Modifier = Modifier,
  query: String,
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
      .padding(horizontal = PaddingMedium)
  ) {
    SearchBar(
      modifier = Modifier.padding(top = PaddingMedium),
      query = query,
      onQueryChange = { dispatch(QueryChange(it)) },
      onBack = { dispatch(BackClick) }
    )
    Spacer(modifier = Modifier.height(PaddingSmall))
    SearchStateContent(
      state = state,
      onLocationClick = { dispatch(LocationSelect(it)) }
    )
  }
}

@Composable
private fun SearchStateContent(
  modifier: Modifier = Modifier,
  state: SearchUiState,
  onLocationClick: (LocationItemUiState) -> Unit
) {
  when (state) {
    is Idle -> Unit
    is Searching -> SearchingIndicator(modifier)
    is Results -> LocationList(
      modifier = modifier,
      emoji = locationPin(),
      locations = state.locations,
      onLocationClick = onLocationClick
    )
    is Recents -> RecentsSection(
      modifier = modifier,
      locations = state.locations,
      onLocationClick = onLocationClick
    )
    is Empty -> EmptyMessage(
      modifier = modifier,
      query = state.query
    )
  }
}

@Composable
private fun RecentsSection(
  modifier: Modifier = Modifier,
  locations: List<LocationItemUiState>,
  onLocationClick: (LocationItemUiState) -> Unit
) {
  Column(modifier = modifier) {
    Text(
      text = recentLocationsTitle(),
      style = typography.titleSmall,
      color = colors.onSurfaceVariant,
      modifier = Modifier.padding(vertical = PaddingSmall)
    )
    LocationList(
      emoji = clock(),
      locations = locations,
      onLocationClick = onLocationClick
    )
  }
}

@Composable
private fun LocationList(
  modifier: Modifier = Modifier,
  emoji: String,
  locations: List<LocationItemUiState>,
  onLocationClick: (LocationItemUiState) -> Unit
) {
  GlassCard(
    modifier = modifier.fillMaxWidth(),
    contentPadding = PaddingValues(0.dp)
  ) {
    locations.forEachIndexed { index, location ->
      LocationItem(
        emoji = emoji,
        name = location.name,
        subtitle = location.subtitle,
        temperature = location.temperature,
        onClick = { onLocationClick(location) }
      )
      if (index < locations.lastIndex) {
        HorizontalDivider(
          color = colors.outline,
          modifier = Modifier.padding(horizontal = PaddingMedium)
        )
      }
    }
  }
}

@Composable
private fun SearchingIndicator(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(
      color = colors.accent,
      modifier = Modifier.padding(PaddingMedium)
    )
  }
}

@Composable
private fun EmptyMessage(
  modifier: Modifier = Modifier,
  query: String
) {
  Text(
    text = noResultsFound(query),
    style = typography.bodyMedium,
    color = colors.onSurfaceVariant,
    modifier = modifier
      .fillMaxWidth()
      .padding(PaddingMedium),
    textAlign = TextAlign.Center
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchPreview::class)
  params: SearchPreviewParams
) {
  WeatherVibeTheme {
    SearchContent(
      query = params.query,
      state = params.state,
      dispatch = {}
    )
  }
}
