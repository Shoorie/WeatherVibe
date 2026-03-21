package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.MAINLY_CLEAR
import com.weather.vibe.domain.weather.model.WeatherCondition.OVERCAST
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN

internal class DailyForecastListPreviewParameterProvider :
  PreviewParameterProvider<List<DailyWeather>> {

  private val weekForecast: List<DailyWeather> =
    listOf(
      DailyWeather("2024-01-15", 22.0, 14.0, PARTLY_CLOUDY, 20),
      DailyWeather("2024-01-16", 19.0, 11.0, RAIN, 75),
      DailyWeather("2024-01-17", 15.0, 8.0, OVERCAST, 30),
      DailyWeather("2024-01-18", 24.0, 16.0, CLEAR_SKY, 5),
      DailyWeather("2024-01-19", 21.0, 13.0, MAINLY_CLEAR, 10),
      DailyWeather("2024-01-20", 17.0, 10.0, DRIZZLE, 60),
      DailyWeather("2024-01-21", 20.0, 12.0, PARTLY_CLOUDY, 25)
    )

  override val values: Sequence<List<DailyWeather>> =
    sequenceOf(weekForecast)
}
