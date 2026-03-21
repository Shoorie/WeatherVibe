package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW

internal class DailyWeatherPreviewParameterProvider :
  PreviewParameterProvider<DailyWeather> {

  private val warmDay: DailyWeather =
    DailyWeather("2024-01-16", 22.0, 14.0, PARTLY_CLOUDY, 20)

  private val coldDay: DailyWeather =
    DailyWeather("2024-01-17", -2.0, -10.0, SNOW, 90)

  override val values: Sequence<DailyWeather> =
    sequenceOf(warmDay, coldDay)
}
