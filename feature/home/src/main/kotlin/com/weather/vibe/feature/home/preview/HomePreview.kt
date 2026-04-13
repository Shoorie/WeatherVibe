package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.preview.HomePreviewData.afternoonSunInfo
import com.weather.vibe.feature.home.preview.HomePreviewData.eightHoursForecast
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import com.weather.vibe.feature.home.preview.HomePreviewData.weekForecast
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.compass
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.eye
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.gauge
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.humidity
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
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

  private val currentWeather: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      currentTemperature = "19°",
      feelsLikeTemperature = "17°",
      highTemperature = "22°",
      lowTemperature = "14°"
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

  private val loadingState: HomeUiState = Loading

  private val errorState: HomeUiState =
    Error("Network connection problem.")

  private val successWithForecast: HomeUiState =
    Loaded(
      currentWeather = currentWeather,
      dailyForecast = weekForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = eightHoursForecast,
      sunriseSunset = afternoonSunInfo
    )

  private val successWithAiContent: HomeUiState =
    Loaded(
      briefing = BriefingUiState.Loaded(
        text = "A mild partly cloudy day with a light breeze — " +
          "great for a walk before the evening rain."
      ),
      currentWeather = currentWeather,
      dailyForecast = weekForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = eightHoursForecast,
      playlist = loadedPlaylist,
      sunriseSunset = afternoonSunInfo
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast,
      successWithAiContent
    )
}
