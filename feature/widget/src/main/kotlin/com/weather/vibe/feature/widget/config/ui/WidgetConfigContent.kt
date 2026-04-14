package com.weather.vibe.feature.widget.config.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.widget.R
import com.weather.vibe.feature.widget.config.preview.WidgetConfigPreview
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Empty
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Error
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Loading
import com.weather.vibe.feature.widget.config.state.WidgetConfigUiState.Ready

@Composable
internal fun WidgetConfigContent(
  state: WidgetConfigUiState,
  onLocationClick: (Long) -> Unit,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxSize()) {
    Text(
      modifier = Modifier.padding(Medium),
      text = stringResource(R.string.widget_config_picker_title),
      style = typography.titleLarge
    )
    when (state) {
      is Loading -> WidgetConfigLoadingContent()
      is Ready -> LocationPickerList(
        locations = state.locations,
        onLocationClick = onLocationClick
      )
      is Empty -> WidgetConfigEmptyContent(hint = state.hint)
      is Error -> WidgetConfigErrorContent(
        message = state.message,
        onRetry = onRetry
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WidgetConfigPreview::class)
  state: WidgetConfigUiState
) {
  WeatherVibeTheme {
    WidgetConfigContent(
      onLocationClick = {},
      onRetry = {},
      state = state
    )
  }
}
