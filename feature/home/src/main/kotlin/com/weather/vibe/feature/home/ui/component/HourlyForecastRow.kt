package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingMedium
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.preview.HourlyForecastListPreview
import com.weather.vibe.feature.home.ui.HomeResources.Texts.hourlyForecastTitle

@Composable
internal fun HourlyForecastRow(
  modifier: Modifier = Modifier,
  hourlyForecasts: List<HourlyForecastUiState>
) {
  GlassCard(
    modifier = modifier.fillMaxWidth(),
    contentPadding = PaddingValues(vertical = PaddingMedium)
  ) {
    Text(
      modifier = Modifier
        .padding(horizontal = PaddingMedium),
      text = hourlyForecastTitle(),
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(PaddingSmall))
    HorizontalDivider(color = colors.outline)
    Spacer(modifier = Modifier.height(PaddingSmall))
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(PaddingExtraSmall),
      contentPadding = PaddingValues(horizontal = PaddingMedium)
    ) {
      items(hourlyForecasts) { hourly ->
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
