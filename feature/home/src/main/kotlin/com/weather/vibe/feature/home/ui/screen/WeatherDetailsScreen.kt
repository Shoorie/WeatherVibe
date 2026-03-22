package com.weather.vibe.feature.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.HomeViewModel
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.preview.HomePreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.atmosphereSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.backContentDescription
import com.weather.vibe.feature.home.ui.HomeResources.Texts.conditionsSectionTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.weatherDetailsTitle
import com.weather.vibe.feature.home.ui.HomeResources.Texts.windSectionTitle
import com.weather.vibe.feature.home.ui.component.DetailSection
import com.weather.vibe.feature.home.ui.component.SunArcSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherDetailsScreen(
  onNavigateBack: () -> Unit = {},
  viewModelStoreOwner: ViewModelStoreOwner =
    requireNotNull(LocalViewModelStoreOwner.current)
) {
  val viewModel: HomeViewModel = koinViewModel(
    viewModelStoreOwner = viewModelStoreOwner
  )
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
  ) {
    when (state) {
      is Loaded -> DetailsLoadedContent(
        state = state,
        onNavigateBack = onNavigateBack
      )
      else -> Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator(color = colors.accent)
      }
    }
  }
}

@Composable
private fun DetailsLoadedContent(
  modifier: Modifier = Modifier,
  state: Loaded,
  onNavigateBack: () -> Unit
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .padding(horizontal = PaddingMedium)
  ) {
    item { DetailsTopBar(onNavigateBack = onNavigateBack) }
    item { Spacer(modifier = Modifier.height(PaddingSmall)) }
    item { SunArcSection(state = state.sunriseSunset) }
    item { Spacer(modifier = Modifier.height(PaddingMedium)) }
    item {
      DetailSection(
        title = windSectionTitle(),
        items = state.detailsSections.wind
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingMedium)) }
    item {
      DetailSection(
        title = atmosphereSectionTitle(),
        items = state.detailsSections.atmosphere
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingMedium)) }
    item {
      DetailSection(
        title = conditionsSectionTitle(),
        items = state.detailsSections.conditions
      )
    }
    item { Spacer(modifier = Modifier.height(PaddingExtraLarge)) }
  }
}

@Composable
private fun DetailsTopBar(
  modifier: Modifier = Modifier,
  onNavigateBack: () -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = PaddingSmall),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(onClick = onNavigateBack) {
      Icon(
        imageVector = AutoMirrored.Filled.ArrowBack,
        contentDescription = backContentDescription(),
        tint = colors.onSurfaceVariant
      )
    }
    Text(
      text = weatherDetailsTitle(),
      style = typography.headlineLarge,
      color = colors.onBackground
    )
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
