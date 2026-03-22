package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.eye
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.precipitation
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunShower
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
      dayLength = "11h 43m",
      sunProgress = 0.65f,
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  private val currentWeather: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      currentTemperature = "19°",
      feelsLikeTemperature = "17°",
      highTemperature = "22°",
      lowTemperature = "14°"
    )

  private val dailyForecast: List<DailyForecastUiState> =
    listOf(
      DailyForecastUiState(partlyCloudy(), "Today", "22°", "14°"),
      DailyForecastUiState(rainfall(), "Tue", "19°", "11°"),
      DailyForecastUiState(cloud(), "Wed", "15°", "8°"),
      DailyForecastUiState(sunny(), "Thu", "24°", "16°"),
      DailyForecastUiState(mostlySunny(), "Fri", "21°", "13°"),
      DailyForecastUiState(sunShower(), "Sat", "17°", "10°"),
      DailyForecastUiState(partlyCloudy(), "Sun", "20°", "12°")
    )

  private val windMetrics: List<MetricItemUiState> =
    listOf(
      MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
      MetricItemUiState(compass(), "Direction", "SW"),
      MetricItemUiState(windGusts(), "Wind Gusts", "20 km/h"),
      MetricItemUiState(windMax(), "Max Wind", "28 km/h")
    )

  private val atmosphereMetrics: List<MetricItemUiState> =
    listOf(
      MetricItemUiState(humidity(), "Humidity", "65%"),
      MetricItemUiState(gauge(), "Pressure", "1015 hPa"),
      MetricItemUiState(dewDrop(), "Dew Point", "12°"),
      MetricItemUiState(cloud(), "Cloud Cover", "45%")
    )

  private val conditionsMetrics: List<MetricItemUiState> =
    listOf(
      MetricItemUiState(precipitation(), "Precipitation", "20%"),
      MetricItemUiState(uvIndex(), "UV Index", "3.5"),
      MetricItemUiState(eye(), "Visibility", "24 km"),
      MetricItemUiState(rainfall(), "Rainfall", "0.2 mm")
    )

  private val detailsSections: DetailsSectionsUiState =
    DetailsSectionsUiState(
      atmosphere = atmosphereMetrics,
      conditions = conditionsMetrics,
      previewItems = listOf(
        atmosphereMetrics[0],
        windMetrics[0],
        conditionsMetrics[1],
        conditionsMetrics[0]
      ),
      wind = windMetrics
    )

  private val hourlyForecast: List<HourlyForecastUiState> =
    List(8) { i ->
      HourlyForecastUiState(
        conditionEmoji = partlyCloudy(),
        isCurrentHour = i == 0,
        temperature = "${18 + i}°",
        timeLabel = "${14 + i}:00"
      )
    }

  private val loadingState: HomeUiState = Loading

  private val errorState: HomeUiState =
    Error("Network connection problem.")

  private val successWithForecast: HomeUiState =
    Loaded(
      currentWeather = currentWeather,
      dailyForecast = dailyForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = hourlyForecast,
      sunriseSunset = sunriseSunset
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast
    )
}
