package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition

internal class HourlyWeatherPreviewParameterProvider :
  PreviewParameterProvider<HourlyWeather> {

  private val dayHour: HourlyWeather =
    HourlyWeather(
      time = "2024-01-15T14:00",
      temperature = 21.0,
      condition = WeatherCondition.PARTLY_CLOUDY,
      humidity = 60,
      windSpeed = 10.0,
      precipitationProbability = 15
    )

  private val nightHour: HourlyWeather =
    HourlyWeather(
      time = "2024-01-15T02:00",
      temperature = -3.0,
      condition = WeatherCondition.CLEAR_SKY,
      humidity = 85,
      windSpeed = 5.0,
      precipitationProbability = 0
    )

  override val values: Sequence<HourlyWeather> =
    sequenceOf(dayHour, nightHour)
}
