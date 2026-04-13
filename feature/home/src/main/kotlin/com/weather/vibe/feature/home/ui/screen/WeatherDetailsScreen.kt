package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.core.designsystem.components.loading.LoadingIndicator
import com.weather.vibe.core.designsystem.components.topbar.VibeTopBar
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.atmosphereSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.conditionsSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSectionTitle
import com.weather.vibe.feature.home.ui.component.DetailSection
import com.weather.vibe.feature.home.ui.component.SunArcSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherDetailsScreen(
  onNavigateBack: () -> Unit = {}
) {

  val owner = LocalContext.current as ViewModelStoreOwner
  val viewModel = koinViewModel<HomeViewModel>(viewModelStoreOwner = owner)
  val state by viewModel.state.collectAsStateWithLifecycle()

  WeatherDetailsContent(
    state = state,
    onNavigateBack = onNavigateBack
  )
}

@Composable
internal fun WeatherDetailsContent(
  modifier: Modifier = Modifier,
  state: HomeUiState,
  onNavigateBack: () -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = colors.backgroundGradientStart,
    topBar = {
      VibeTopBar(
        title = weatherDetailsTitle(),
        onNavigateBack = onNavigateBack
      )
    }
  ) { innerPadding ->
    when (state) {
      is Loading,
      is Error -> LoadingIndicator(modifier = Modifier.fillMaxSize())
      is Loaded -> DetailsLoadedContent(
        modifier = Modifier.padding(innerPadding),
        state = state
      )
    }
  }
}

@Composable
private fun DetailsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = Padding.Medium)
  ) {
    item { Spacer(modifier = Modifier.height(Padding.Small)) }
    item { SunArcSection(state = state.sunriseSunset) }
    item { Spacer(modifier = Modifier.height(Padding.Medium)) }
    item {
      DetailSection(
        title = windSectionTitle(),
        items = state.detailsSections.wind
      )
    }
    item { Spacer(modifier = Modifier.height(Padding.Medium)) }
    item {
      DetailSection(
        title = atmosphereSectionTitle(),
        items = state.detailsSections.atmosphere
      )
    }
    item { Spacer(modifier = Modifier.height(Padding.Medium)) }
    item {
      DetailSection(
        title = conditionsSectionTitle(),
        items = state.detailsSections.conditions
      )
    }
    item { Spacer(modifier = Modifier.height(Padding.ExtraLarge)) }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HomePreview::class)
  state: HomeUiState
) {
  WeatherVibeTheme {
    WeatherDetailsContent(
      state = state,
      onNavigateBack = {}
    )
  }
}
