package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.weather.model.LocationResult
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.HomeAction
import com.weather.vibe.feature.home.presentation.HomeAction.DismissSearch
import com.weather.vibe.feature.home.presentation.HomeAction.LocationSelect
import com.weather.vibe.feature.home.presentation.HomeAction.QueryChange
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ToggleSearch
import com.weather.vibe.feature.home.presentation.HomeUiState
import com.weather.vibe.feature.home.presentation.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.SearchState
import com.weather.vibe.feature.home.preview.HomePreviewParameterProvider
import com.weather.vibe.feature.home.preview.HomePreviewParams
import com.weather.vibe.feature.home.ui.HomeResources.Texts.noResultsFound
import com.weather.vibe.feature.home.ui.HomeResources.Texts.refreshContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.searchCityContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.tryAgainContentDescription
import com.weather.vibe.feature.home.ui.component.CurrentWeatherSection
import com.weather.vibe.feature.home.ui.component.DailyForecastList
import com.weather.vibe.feature.home.ui.component.HourlyForecastRow
import com.weather.vibe.feature.home.ui.component.LocationSearchBar
import com.weather.vibe.feature.home.ui.component.LocationSearchResults
import com.weather.vibe.feature.home.ui.component.WeatherMetricsGrid
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen() {

  val viewModel: HomeViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val searchState by viewModel.searchState.collectAsStateWithLifecycle()

  HomeContent(
    state = state,
    searchState = searchState,
    dispatch = viewModel::dispatch
  )
}

@Composable
private fun HomeContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  searchState: SearchState,
  dispatch: (HomeAction) -> Unit
) {
  val colors = colors
  val backgroundBrush = Brush.verticalGradient(
    listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(brush = backgroundBrush)
  ) {
    when (state) {
      is Loading -> LoadingContent()
      is Error -> ErrorContent(
        error = state.message,
        onRetry = { dispatch(RefreshClick) }
      )
      is Loaded -> WeatherContent(
        weatherData = state.weatherData,
        onRefresh = { dispatch(RefreshClick) },
        onSearchToggle = { dispatch(ToggleSearch) }
      )
    }

    if (searchState.isActive) {
      SearchOverlay(
        query = searchState.query,
        results = searchState.results,
        isSearching = searchState.isSearching,
        onQueryChange = { dispatch(QueryChange(it)) },
        onLocationSelected = { dispatch(LocationSelect(it)) },
        onDismiss = { dispatch(DismissSearch) }
      )
    }
  }
}

@Composable
private fun WeatherContent(
  modifier: Modifier = Modifier,
  weatherData: WeatherData,
  onRefresh: () -> Unit,
  onSearchToggle: () -> Unit
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .padding(horizontal = PaddingMedium)
  ) {
    item {
      LocationHeader(
        cityName = weatherData.cityName,
        onRefresh = onRefresh,
        onSearchToggle = onSearchToggle
      )
    }
    item { CurrentWeatherSection(weatherData = weatherData) }
    item { HourlyForecastRow(hourlyForecasts = weatherData.hourlyForecast) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item { DailyForecastList(dailyForecasts = weatherData.dailyForecast) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item {
      WeatherMetricsGrid(
        humidity = weatherData.humidity,
        windSpeed = weatherData.windSpeed,
        windDirection = weatherData.windDirection,
        precipitationProbability = weatherData.hourlyForecast
          .firstOrNull()?.precipitationProbability ?: 0
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingExtraLarge)) }
  }
}

@Composable
private fun LocationHeader(
  modifier: Modifier = Modifier,
  cityName: String,
  onRefresh: () -> Unit,
  onSearchToggle: () -> Unit
) {
  val colors = colors
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = PaddingMedium),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = cityName,
        style = typography.headlineLarge,
        color = colors.onBackground
      )
      Text(
        text = currentDateLabel(),
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant
      )
    }
    IconButton(onClick = onSearchToggle) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = searchCityContentDescription(),
        tint = colors.onSurfaceVariant
      )
    }
    IconButton(onClick = onRefresh) {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = refreshContentDescription(),
        tint = colors.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun SearchOverlay(
  modifier: Modifier = Modifier,
  query: String,
  results: List<LocationResult>,
  isSearching: Boolean,
  onQueryChange: (String) -> Unit,
  onLocationSelected: (LocationResult) -> Unit,
  onDismiss: () -> Unit
) {

  val interactionSource = remember { MutableInteractionSource() }
  val backgroundBrush = Brush.verticalGradient(
    listOf(
      colors.backgroundGradientStart,
      colors.backgroundGradientEnd
    )
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(brush = backgroundBrush)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onDismiss
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(PaddingMedium)
        .clickable(
          interactionSource = interactionSource,
          indication = null,
          onClick = {}
        )
    ) {
      LocationSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onDismiss = onDismiss
      )
      Spacer(modifier = Modifier.height(PaddingSmall))
      when {
        isSearching -> Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(
            color = colors.accent,
            modifier = Modifier.padding(PaddingMedium)
          )
        }
        results.isNotEmpty() -> LocationSearchResults(
          results = results,
          onLocationSelected = onLocationSelected
        )
        query.length >= 2 -> Text(
          text = noResultsFound(query),
          style = typography.bodyMedium,
          color = colors.onSurfaceVariant,
          modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingMedium),
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(color = colors.accent)
  }
}

@Composable
private fun ErrorContent(
  modifier: Modifier = Modifier,
  error: String,
  onRetry: () -> Unit
) {
  val colors = colors
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(text = "\u26A1", style = typography.displaySmall)
    Spacer(modifier = Modifier.height(PaddingMedium))
    Text(
      text = error,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(horizontal = PaddingLarge)
    )
    Spacer(modifier = Modifier.height(PaddingLarge))
    IconButton(onClick = onRetry) {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = tryAgainContentDescription(),
        tint = colors.accent
      )
    }
  }
}

private fun currentDateLabel(): String = runCatching {
  LocalDate.now().format(
    DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
  )
}.getOrDefault("")

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HomePreviewParameterProvider::class)
  params: HomePreviewParams
) {
  WeatherVibeTheme {
    HomeContent(
      state = params.state,
      searchState = params.searchState,
      dispatch = {}
    )
  }
}
