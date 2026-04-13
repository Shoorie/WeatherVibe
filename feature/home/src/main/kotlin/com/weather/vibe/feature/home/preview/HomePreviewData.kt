package com.weather.vibe.feature.home.preview

import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal object HomePreviewData {

  val header: HeaderUiState =
    HeaderUiState(
      cityName = "Zielona Góra",
      dateLabel = "Saturday, 22 March"
    )

  val warmDayCurrent: CurrentWeatherUiState =
    CurrentWeatherUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      currentTemperature = "19°",
      feelsLikeTemperature = "17°",
      highTemperature = "22°",
      lowTemperature = "14°"
    )

  val afternoonSunInfo: SunriseSunsetUiState =
    SunriseSunsetUiState(
      dayLength = "11h 43m",
      sunProgress = 0.65f,
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  val nighttimeSunInfo: SunriseSunsetUiState =
    SunriseSunsetUiState(
      dayLength = "11h 43m",
      sunProgress = 0f,
      sunriseTime = "06:24",
      sunsetTime = "18:07"
    )

  private val today: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      dayLabel = "Today",
      isToday = true,
      maxTemperature = "22°",
      minTemperature = "14°",
      range = DailyRangeUiState(
        startFraction = 0.7f,
        endFraction = 0.95f,
        currentFraction = 0.85f
      )
    )

  private val tuesday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = rainfall(),
      conditionLabel = "Rain",
      dayLabel = "Tue",
      isToday = false,
      maxTemperature = "19°",
      minTemperature = "11°",
      range = DailyRangeUiState(
        startFraction = 0.55f,
        endFraction = 0.78f,
        currentFraction = null
      )
    )

  private val wednesday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = cloud(),
      conditionLabel = "Cloudy",
      dayLabel = "Wed",
      isToday = false,
      maxTemperature = "15°",
      minTemperature = "8°",
      range = DailyRangeUiState(
        startFraction = 0.4f,
        endFraction = 0.55f,
        currentFraction = null
      )
    )

  private val thursday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = sunny(),
      conditionLabel = "Sunny",
      dayLabel = "Thu",
      isToday = false,
      maxTemperature = "24°",
      minTemperature = "16°",
      range = DailyRangeUiState(
        startFraction = 0.78f,
        endFraction = 1f,
        currentFraction = null
      )
    )

  private val friday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = mostlySunny(),
      conditionLabel = "Mostly Sunny",
      dayLabel = "Fri",
      isToday = false,
      maxTemperature = "21°",
      minTemperature = "13°",
      range = DailyRangeUiState(
        startFraction = 0.62f,
        endFraction = 0.85f,
        currentFraction = null
      )
    )

  private val saturday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = sunShower(),
      conditionLabel = "Showers",
      dayLabel = "Sat",
      isToday = false,
      maxTemperature = "17°",
      minTemperature = "10°",
      range = DailyRangeUiState(
        startFraction = 0.48f,
        endFraction = 0.65f,
        currentFraction = null
      )
    )

  private val sunday: DailyForecastUiState =
    DailyForecastUiState(
      conditionEmoji = partlyCloudy(),
      conditionLabel = "Partly Cloudy",
      dayLabel = "Sun",
      isToday = false,
      maxTemperature = "20°",
      minTemperature = "12°",
      range = DailyRangeUiState(
        startFraction = 0.58f,
        endFraction = 0.8f,
        currentFraction = null
      )
    )

  val weekForecast: DailyForecastsUiState = DailyForecastsUiState(
    items = persistentListOf(today, tuesday, wednesday, thursday, friday, saturday, sunday)
  )

  private val hourlyEmojis: List<String> =
    listOf(
      partlyCloudy(),
      cloud(),
      mostlySunny(),
      sunny(),
      partlyCloudy(),
      rainfall(),
      cloud(),
      mostlySunny()
    )

  val eightHoursForecast: HourlyForecastsUiState = HourlyForecastsUiState(
    items = List(hourlyEmojis.size) { index ->
      HourlyForecastUiState(
        conditionEmoji = hourlyEmojis[index],
        isCurrentHour = index == 0,
        temperature = "${18 + index}°",
        timeLabel = "${14 + index}:00"
      )
    }.toImmutableList()
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

  val detailsSections: DetailsSectionsUiState =
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

  val loadedPlaylist: PlaylistUiState.Loaded =
    PlaylistUiState.Loaded(
      genres = listOf(
        GenreChipUiState(name = "lo-fi hip hop"),
        GenreChipUiState(name = "acoustic"),
        GenreChipUiState(name = "rainy day indie")
      ),
      mood = "Cozy rainy afternoon",
      moodDescription = "Stay in, grab a warm drink, and let the music match the rain",
      spotifyQuery = "spotify:search:lo-fi hip hop acoustic rainy day indie",
      ytMusicUrl = "https://music.youtube.com/search?q=lo-fi+hip+hop"
    )
}
