package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.BriefingUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
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
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunShower
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny
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

  private val dailyForecast: DailyForecastsUiState = DailyForecastsUiState(
    items = listOf(
      day("Today", partlyCloudy(), "Partly Cloudy", "22°", "14°", 0.7f, 0.95f, 0.85f, true),
      day("Tue", rainfall(), "Rain", "19°", "11°", 0.55f, 0.78f),
      day("Wed", cloud(), "Cloudy", "15°", "8°", 0.4f, 0.55f),
      day("Thu", sunny(), "Sunny", "24°", "16°", 0.78f, 1f),
      day("Fri", mostlySunny(), "Mostly Sunny", "21°", "13°", 0.62f, 0.85f),
      day("Sat", sunShower(), "Showers", "17°", "10°", 0.48f, 0.65f),
      day("Sun", partlyCloudy(), "Partly Cloudy", "20°", "12°", 0.58f, 0.8f)
    )
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

  private val hourlyForecast: HourlyForecastsUiState = HourlyForecastsUiState(
    items = List(8) { i ->
      HourlyForecastUiState(
        conditionEmoji = partlyCloudy(),
        isCurrentHour = i == 0,
        temperature = "${18 + i}°",
        timeLabel = "${14 + i}:00"
      )
    }
  )

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

  private val successWithAiContent: HomeUiState =
    Loaded(
      briefing = BriefingUiState.Loaded(
        text = "A mild partly cloudy day with a light breeze — " +
          "great for a walk before the evening rain."
      ),
      currentWeather = currentWeather,
      dailyForecast = dailyForecast,
      detailsSections = detailsSections,
      header = header,
      hourlyForecast = hourlyForecast,
      playlist = PlaylistUiState.Loaded(
        genres = listOf(
          GenreChipUiState(name = "lo-fi hip hop"),
          GenreChipUiState(name = "acoustic"),
          GenreChipUiState(name = "rainy day indie")
        ),
        mood = "Cozy rainy afternoon",
        moodDescription = "Stay in, grab a warm drink",
        spotifyQuery = "spotify:search:lo-fi hip hop acoustic rainy day indie",
        ytMusicUrl = "https://music.youtube.com/search?q=lo-fi+hip+hop"
      ),
      sunriseSunset = sunriseSunset
    )

  override val values: Sequence<HomeUiState> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast,
      successWithAiContent
    )

  @Suppress("LongParameterList")
  private fun day(
    label: String,
    emoji: String,
    condition: String,
    max: String,
    min: String,
    rangeStart: Float,
    rangeEnd: Float,
    current: Float? = null,
    isToday: Boolean = false
  ): DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = emoji,
      conditionLabel = condition,
      dayLabel = label,
      isToday = isToday,
      maxTemperature = max,
      minTemperature = min,
      range = DailyRangeUiState(
        startFraction = rangeStart,
        endFraction = rangeEnd,
        currentFraction = current
      )
    )
}
