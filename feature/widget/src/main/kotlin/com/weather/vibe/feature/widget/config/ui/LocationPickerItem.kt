package com.weather.vibe.feature.widget.config.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.widget.config.preview.LocationPickerItemPreview
import com.weather.vibe.feature.widget.config.state.LocationPickerItemUiState

@Composable
internal fun LocationPickerItem(
  state: LocationPickerItemUiState,
  onClick: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick(state.id) }
      .padding(horizontal = Medium, vertical = Small)
  ) {
    Text(
      text = state.name,
      style = typography.titleMedium
    )
    Text(
      text = state.subtitle,
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(LocationPickerItemPreview::class)
  state: LocationPickerItemUiState
) {
  WeatherVibeTheme {
    LocationPickerItem(state = state, onClick = {})
  }
}
