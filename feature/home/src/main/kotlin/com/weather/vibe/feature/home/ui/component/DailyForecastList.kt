package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.feature.home.preview.DailyForecastListPreviewParameterProvider
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dailyForecastTitle

@Composable
internal fun DailyForecastList(
  modifier: Modifier = Modifier,
  dailyForecasts: List<DailyWeather>
) {
  GlassCard(modifier = modifier.fillMaxWidth()) {
    Text(
      text = dailyForecastTitle(),
      style = typography.titleSmall,
      color = colors.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(PaddingSmall))
    HorizontalDivider(color = colors.outline)
    dailyForecasts.forEachIndexed { index, daily ->
      DailyForecastItem(dailyWeather = daily)
      if (index < dailyForecasts.lastIndex) {
        HorizontalDivider(color = colors.outline)
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(DailyForecastListPreviewParameterProvider::class)
  forecasts: List<DailyWeather>
) {
  WeatherVibeTheme {
    DailyForecastList(dailyForecasts = forecasts)
  }
}
