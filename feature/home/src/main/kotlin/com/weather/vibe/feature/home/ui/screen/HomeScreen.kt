package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.HomeAction
import com.weather.vibe.feature.home.presentation.HomeAction.ReceiveLocationResult
import com.weather.vibe.feature.home.presentation.HomeAction.RefreshClick
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.error
import com.weather.vibe.feature.home.ui.HomeResources.Texts.refreshContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.searchCityContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.settingsContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.tryAgainContentDescription
import com.weather.vibe.feature.home.ui.component.AiBriefingCard
import com.weather.vibe.feature.home.ui.component.CurrentWeatherSection
import com.weather.vibe.feature.home.ui.component.DailyForecastList
import com.weather.vibe.feature.home.ui.component.DetailsPreviewCard
import com.weather.vibe.feature.home.ui.component.HourlyForecastRow
import com.weather.vibe.feature.home.ui.component.MoodPlaylistSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
  onNavigateToDetails: () -> Unit = {},
  onNavigateToSearch: () -> Unit = {},
  onNavigateToSettings: () -> Unit = {},
  selectedCityName: String? = null,
  selectedLatitude: Double? = null,
  selectedLongitude: Double? = null
) {

  val viewModel: HomeViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect(lifecycleOwner) {
    val observer = object : DefaultLifecycleObserver {
      override fun onResume(owner: LifecycleOwner) {
        viewModel.dispatch(ResumeLifecycle)
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  LaunchedEffect(selectedCityName) {
    if (selectedCityName != null
      && selectedLatitude != null
      && selectedLongitude != null
    ) {
      viewModel.dispatch(
        ReceiveLocationResult(
          cityName = selectedCityName,
          latitude = selectedLatitude,
          longitude = selectedLongitude
        )
      )
    }
  }

  HomeContent(
    state = state,
    dispatch = viewModel::dispatch,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings
  )
}

@Composable
internal fun HomeContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  dispatch: (HomeAction) -> Unit,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit
) {
  val gradientStart = colors.backgroundGradientStart
  val gradientEnd = colors.backgroundGradientEnd
  val backgroundBrush = remember(gradientStart, gradientEnd) {
    Brush.verticalGradient(listOf(gradientStart, gradientEnd))
  }

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
        state = state,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onRefresh = { dispatch(RefreshClick) }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit
) {

  var showMoodSheet by rememberSaveable { mutableStateOf(value = false) }
  val uriHandler = LocalUriHandler.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .padding(horizontal = PaddingMedium)
  ) {
    item {
      LocationHeader(
        state = state.header,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onRefresh = onRefresh
      )
    }
    item { CurrentWeatherSection(state = state.currentWeather) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item {
      AiBriefingCard(
        onMusicClick = { showMoodSheet = true },
        state = state.briefing
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item { HourlyForecastRow(hourlyForecasts = state.hourlyForecast) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item { DailyForecastList(dailyForecasts = state.dailyForecast) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item {
      DetailsPreviewCard(
        previewItems = state.detailsSections.previewItems,
        onClick = { onNavigateToDetails() }
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingExtraLarge)) }
  }

  if (showMoodSheet) {
    MoodPlaylistSheet(
      onDismiss = { showMoodSheet = false },
      onOpenSpotify = { query -> runCatching { uriHandler.openUri(query) } },
      onOpenYtMusic = { url -> runCatching { uriHandler.openUri(url) } },
      state = state.playlist
    )
  }
}

@Composable
private fun LocationHeader(
  modifier: Modifier = Modifier,
  state: HeaderUiState,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = PaddingMedium),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = state.cityName,
        style = typography.headlineLarge,
        color = colors.onBackground,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = state.dateLabel,
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant
      )
    }
    IconButton(onClick = onNavigateToSearch) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = searchCityContentDescription(),
        tint = colors.onSurfaceVariant
      )
    }
    IconButton(onClick = onNavigateToSettings) {
      Icon(
        imageVector = Icons.Default.Settings,
        contentDescription = settingsContentDescription(),
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
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = error(),
      style = typography.displaySmall
    )
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

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HomePreview::class)
  state: HomeUiState
) {
  WeatherVibeTheme {
    HomeContent(
      state = state,
      dispatch = {},
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {}
    )
  }
}
