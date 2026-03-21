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
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.preview.WeatherDataPreviewParameterProvider
import kotlin.math.roundToInt

@Composable
internal fun CurrentWeatherSection(
  modifier: Modifier = Modifier,
  weatherData: WeatherData
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = PaddingLarge),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = weatherData.condition.emoji,
      fontSize = EmojiSizeLarge
    )

    Spacer(modifier = Modifier.height(PaddingSmall))

    Text(
      text = "${weatherData.currentTemperature.roundToInt()}°",
      style = typography.displayLarge,
      color = colors.onBackground,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(PaddingExtraSmall))

    Text(
      text = weatherData.condition.label,
      style = typography.titleMedium,
      color = colors.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(PaddingMedium))

    weatherData.dailyForecast.firstOrNull()?.let { today ->
      HighLowTemperatureRow(today = today)
    }
  }
}

@Composable
private fun HighLowTemperatureRow(
  modifier: Modifier = Modifier,
  today: DailyWeather
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(PaddingMedium)
  ) {
    Text(
      text = "H: ${today.maxTemperature.roundToInt()}°",
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
    Text(
      text = "L: ${today.minTemperature.roundToInt()}°",
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant
    )
  }
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(WeatherDataPreviewParameterProvider::class)
  weatherData: WeatherData
) {
  WeatherVibeTheme {
    CurrentWeatherSection(weatherData = weatherData)
  }
}
