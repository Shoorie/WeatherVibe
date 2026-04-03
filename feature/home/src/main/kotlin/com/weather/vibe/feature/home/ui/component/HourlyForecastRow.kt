package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCardSection
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.preview.HourlyForecastListPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.hourlyForecastTitle

@Composable
internal fun HourlyForecastRow(
  modifier: Modifier = Modifier,
  hourlyForecasts: List<HourlyForecastUiState>
) {
  GlassCardSection(
    modifier = modifier,
    title = hourlyForecastTitle(),
    contentPadding = PaddingValues(vertical = PaddingMedium)
  ) {
    Spacer(modifier = Modifier.height(PaddingSmall))
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(PaddingExtraSmall),
      contentPadding = PaddingValues(horizontal = PaddingMedium)
    ) {
      items(
        items = hourlyForecasts,
        key = { it.timeLabel }
      ) { hourly ->
        HourlyForecastItem(state = hourly)
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(HourlyForecastListPreview::class)
  forecasts: List<HourlyForecastUiState>
) {
  WeatherVibeTheme {
    HourlyForecastRow(hourlyForecasts = forecasts)
  }
}
