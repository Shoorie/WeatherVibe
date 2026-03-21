package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition

internal class HourlyForecastListPreviewParameterProvider :
  PreviewParameterProvider<List<HourlyWeather>> {

  private val eightHours: List<HourlyWeather> =
    List(8) { index ->
      HourlyWeather(
        time = "2024-01-15T${14 + index}:00",
        temperature = 18.0 + index,
        condition = WeatherCondition.entries[
          index % WeatherCondition.entries.size
        ],
        humidity = 60 + index,
        windSpeed = 10.0,
        precipitationProbability = index * 5
      )
    }

  override val values: Sequence<List<HourlyWeather>> =
    sequenceOf(eightHours)
}
