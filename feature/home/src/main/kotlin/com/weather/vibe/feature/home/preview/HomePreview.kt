package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.eye
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.wind
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windGusts
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.windMax

internal class HomePreview :
  PreviewParameterProvider<HomeUiState> {

  private val header: HeaderUiState =
    HeaderUiState(
      cityName = "Zielona Góra",
      dateLabel = "Saturday, 22 March"
    )

  private val sunriseSunset: SunriseSunsetUiState =
    SunriseSunsetUiState(
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  private val currentWeather: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = "⛅",
      conditionLabel = "Partly Cloudy",
      currentTemperature = "19°",
      feelsLikeTemperature = "17°",
      highTemperature = "22°",
      lowTemperature = "14°"
    )

  private val dailyForecast: List<DailyForecastUiState> =
    listOf(
      DailyForecastUiState("⛅", "Today", "22°", "14°"),
      DailyForecastUiState("🌧️", "Tue", "19°", "11°"),
      DailyForecastUiState("☁️", "Wed", "15°", "8°"),
      DailyForecastUiState("☀️", "Thu", "24°", "16°"),
      DailyForecastUiState("🌤️", "Fri", "21°", "13°"),
      DailyForecastUiState("🌦️", "Sat", "17°", "10°"),
      DailyForecastUiState("⛅", "Sun", "20°", "12°")
    )

  private val metrics: List<MetricItemUiState> =
    listOf(
      MetricItemUiState(humidity(), "Humidity", "65%"),
      MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
      MetricItemUiState(compass(), "Direction", "SW"),
      MetricItemUiState(precipitation(), "Precipitation", "20%"),
      MetricItemUiState(uvIndex(), "UV Index", "3.5"),
      MetricItemUiState(cloud(), "Cloud Cover", "45%"),
      MetricItemUiState(gauge(), "Pressure", "1015 hPa"),
      MetricItemUiState(eye(), "Visibility", "24 km"),
      MetricItemUiState(dewDrop(), "Dew Point", "12\u00B0"),
      MetricItemUiState(windGusts(), "Wind Gusts", "20 km/h"),
      MetricItemUiState(windMax(), "Max Wind", "28 km/h"),
      MetricItemUiState(rainfall(), "Rainfall", "0.2 mm")
    )

  private val hourlyForecast: List<HourlyForecastUiState> =
    List(8) { i ->
      HourlyForecastUiState(
        conditionEmoji = "⛅",
        isCurrentHour = i == 0,
        temperature = "${18 + i}°",
        timeLabel = "${14 + i}:00"
      )
    }

  private val loadingState: HomeUiState = Loading

  private val errorState: HomeUiState =
    Error("Brak połączenia z internetem.")

  private val successWithForecast: HomeUiState =
    Loaded(
      currentWeather = currentWeather,
      dailyForecast = dailyForecast,
      header = header,
      hourlyForecast = hourlyForecast,
      metrics = MetricsUiState(items = metrics),
      sunriseSunset = sunriseSunset
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast
    )
}
