package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.EmojiSizeLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.preview.CurrentWeatherPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.feelsLikeLabel

@Composable
internal fun CurrentWeatherSection(
  modifier: Modifier = Modifier,
  state: CurrentWeatherUiState
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = PaddingLarge),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = state.conditionEmoji,
      fontSize = EmojiSizeLarge
    )

    Spacer(modifier = Modifier.height(PaddingSmall))

    Text(
      text = state.currentTemperature,
      style = typography.displayLarge,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )

    Text(
      text = feelsLikeLabel(state.feelsLikeTemperature),
      style = typography.bodySmall,
      color = colors.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(PaddingExtraSmall))

    Text(
      text = state.conditionLabel,
      style = typography.titleMedium,
      color = colors.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(PaddingMedium))

    HighLowTemperatureRow(
      highTemperature = state.highTemperature,
      lowTemperature = state.lowTemperature
    )
  }
}

@Composable
private fun HighLowTemperatureRow(
  modifier: Modifier = Modifier,
  highTemperature: String,
  lowTemperature: String
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(PaddingMedium)
  ) {
    Text(
      text = "H: $highTemperature",
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
    Text(
      text = "L: $lowTemperature",
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(CurrentWeatherPreview::class)
  state: CurrentWeatherUiState
) {
  WeatherVibeTheme {
    CurrentWeatherSection(state = state)
  }
}
