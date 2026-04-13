package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.home.presentation.HomeAction.Initialize
import com.weather.vibe.feature.home.presentation.HomeAction.ResumeLifecycle
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeKeys.BRIEFING
import com.weather.vibe.feature.home.ui.HomeKeys.DAILY
import com.weather.vibe.feature.home.ui.HomeKeys.DETAILS
import com.weather.vibe.feature.home.ui.HomeKeys.HERO
import com.weather.vibe.feature.home.ui.HomeKeys.HOURLY
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.error
import com.weather.vibe.feature.home.ui.HomeResources.Texts.tryAgainContentDescription
import com.weather.vibe.feature.home.ui.HomeTestTags.FORECAST_LIST
import com.weather.vibe.feature.home.ui.component.DailyForecastList
import com.weather.vibe.feature.home.ui.component.DetailsPreviewCard
import com.weather.vibe.feature.home.ui.component.HomeHeroCard
import com.weather.vibe.feature.home.ui.component.HourlyForecastRow
import com.weather.vibe.feature.home.ui.component.MoodPlaylistSheet
import com.weather.vibe.feature.home.ui.component.WeatherBriefingCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
  onNavigateToDetails: () -> Unit = {},
  onNavigateToSearch: () -> Unit = {},
  onNavigateToSettings: () -> Unit = {},
  selectedLocation: Location? = null
) {

  val owner = LocalContext.current as ViewModelStoreOwner
  val viewModel = koinViewModel<HomeViewModel>(viewModelStoreOwner = owner)
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

  LaunchedEffect(selectedLocation) {
    viewModel.dispatch(Initialize(selectedLocation))
  }

  val callbacks = remember(viewModel) { HomeCallbacks(viewModel) }

  HomeContent(
    state = state,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings,
    onRefresh = callbacks.onRefresh,
    onRetrySuggestion = callbacks.onRetrySuggestion,
    onGenreRemoveClick = callbacks.onGenreRemoveClick
  )
}

@Composable
internal fun HomeContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onGenreRemoveClick: (String) -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(color = colors.backgroundGradientEnd)
  ) {
    when (state) {
      is Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
      is Error -> ErrorContent(
        error = state.message,
        onRetry = onRefresh
      )
      is Loaded -> WeatherContent(
        state = state,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToSettings = onNavigateToSettings,
        onRefresh = onRefresh,
        onRetrySuggestion = onRetrySuggestion,
        onGenreRemoveClick = onGenreRemoveClick
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
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onGenreRemoveClick: (String) -> Unit
) {

  var showMoodSheet by rememberSaveable { mutableStateOf(value = false) }
  val uriHandler = LocalUriHandler.current
  val sheetCallbacks = remember(uriHandler) {
    MoodSheetCallbacks(uriHandler) { showMoodSheet = it }
  }

  ForecastList(
    modifier = modifier,
    state = state,
    onNavigateToDetails = onNavigateToDetails,
    onNavigateToSearch = onNavigateToSearch,
    onNavigateToSettings = onNavigateToSettings,
    onRefresh = onRefresh,
    onRetrySuggestion = onRetrySuggestion,
    onMusicClick = sheetCallbacks.onShow
  )

  if (showMoodSheet) {
    MoodPlaylistSheet(
      onDismiss = sheetCallbacks.onDismiss,
      onGenreRemoveClick = onGenreRemoveClick,
      onOpenSpotify = sheetCallbacks.onOpenSpotify,
      onOpenYtMusic = sheetCallbacks.onOpenYtMusic,
      state = state.playlist
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ForecastList(
  modifier: Modifier = Modifier,
  state: Loaded,
  onNavigateToDetails: () -> Unit,
  onNavigateToSearch: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onRefresh: () -> Unit,
  onRetrySuggestion: () -> Unit,
  onMusicClick: () -> Unit
) {

  val horizontalPadding = remember { Modifier.padding(horizontal = Padding.Medium) }
  val listContentPadding = remember {
    PaddingValues(top = Padding.Medium, bottom = Padding.ExtraLarge)
  }

  PullToRefreshBox(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding(),
    isRefreshing = state.isRefreshing,
    onRefresh = onRefresh
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag(FORECAST_LIST),
      contentPadding = listContentPadding,
      verticalArrangement = Arrangement.spacedBy(Padding.Large)
    ) {
      item(key = HERO) {
        HomeHeroCard(
          modifier = horizontalPadding,
          header = state.header,
          currentWeather = state.currentWeather,
          onNavigateToSearch = onNavigateToSearch,
          onNavigateToSettings = onNavigateToSettings
        )
      }
      item(key = BRIEFING) {
        WeatherBriefingCard(
          modifier = horizontalPadding,
          onMusicClick = onMusicClick,
          onRetryClick = onRetrySuggestion,
          state = state.briefing
        )
      }
      item(key = HOURLY) {
        HourlyForecastRow(state = state.hourlyForecast)
      }
      item(key = DAILY) {
        DailyForecastList(
          modifier = horizontalPadding,
          state = state.dailyForecast
        )
      }
      item(key = DETAILS) {
        DetailsPreviewCard(
          modifier = horizontalPadding,
          previewItems = state.detailsSections.previewItems,
          onClick = onNavigateToDetails
        )
      }
    }
  }
}

@Composable
private fun ErrorContent(
  modifier: Modifier = Modifier,
  error: String,
  onRetry: () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .semantics { liveRegion = LiveRegionMode.Polite },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      modifier = Modifier.clearAndSetSemantics {},
      text = error(),
      style = typography.displaySmall
    )
    Spacer(modifier = Modifier.height(Padding.Medium))
    Text(
      text = error,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(horizontal = Padding.Large)
    )
    Spacer(modifier = Modifier.height(Padding.Large))
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
      onNavigateToDetails = {},
      onNavigateToSearch = {},
      onNavigateToSettings = {},
      onRefresh = {},
      onRetrySuggestion = {},
      onGenreRemoveClick = {}
    )
  }
}
