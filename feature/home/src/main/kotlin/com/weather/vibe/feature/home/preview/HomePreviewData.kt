package com.weather.vibe.feature.home.preview

import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.cloud
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.rainfall
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunShower
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunny
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal object HomePreviewData {

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
