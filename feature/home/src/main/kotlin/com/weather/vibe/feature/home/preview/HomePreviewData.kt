package com.weather.vibe.feature.home.preview

import com.weather.vibe.feature.home.presentation.state.AiSuggestionSectionUiState
import com.weather.vibe.feature.home.presentation.state.AirQualityChipUiState
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.EnvChipTint
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.DailyRangeUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeCardUiState
import com.weather.vibe.feature.home.presentation.state.DailyVibeUiState
import com.weather.vibe.feature.home.presentation.state.DetailsSectionsUiState
import com.weather.vibe.feature.home.presentation.state.ForecastSectionUiState
import com.weather.vibe.feature.home.presentation.state.GenreChipUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HomeAlertUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastsUiState
import com.weather.vibe.feature.home.presentation.state.MetricItemUiState
import com.weather.vibe.feature.home.presentation.state.PlaylistUiState
import com.weather.vibe.feature.home.presentation.state.PollenChipUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.ui.HomeAirQualityResources.Emojis
import com.weather.vibe.feature.home.ui.HomeEmojis.cloud
import com.weather.vibe.feature.home.ui.HomeEmojis.compass
import com.weather.vibe.feature.home.ui.HomeEmojis.dewDrop
import com.weather.vibe.feature.home.ui.HomeEmojis.eye
import com.weather.vibe.feature.home.ui.HomeEmojis.gauge
import com.weather.vibe.feature.home.ui.HomeEmojis.humidity
import com.weather.vibe.feature.home.ui.HomeEmojis.mostlySunny
import com.weather.vibe.feature.home.ui.HomeEmojis.partlyCloudy
import com.weather.vibe.feature.home.ui.HomeEmojis.precipitation
import com.weather.vibe.feature.home.ui.HomeEmojis.rainfall
import com.weather.vibe.feature.home.ui.HomeEmojis.sunShower
import com.weather.vibe.feature.home.ui.HomeEmojis.sunny
import com.weather.vibe.feature.home.ui.HomeEmojis.uvIndex
import com.weather.vibe.feature.home.ui.HomeEmojis.vibePleasant
import com.weather.vibe.feature.home.ui.HomeEmojis.vibeRough
import com.weather.vibe.feature.home.ui.HomeEmojis.wind
import com.weather.vibe.feature.home.ui.HomeEmojis.windGusts
import com.weather.vibe.feature.home.ui.HomeEmojis.windMax
import kotlinx.collections.immutable.ImmutableList
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

  val pleasantVibe: DailyVibeUiState =
    DailyVibeUiState(
      contentDescription = "Pleasant vibes, vibe score 78 out of 100. Solid vibes all around.",
      emoji = vibePleasant(),
      oneLiner = "Solid vibes all around.",
      summary = "78/100  ·  Pleasant vibes"
    )

  val roughVibe: DailyVibeUiState =
    DailyVibeUiState(
      contentDescription = "Rough out there, vibe score 22 out of 100. Stay in, queue up a show.",
      emoji = vibeRough(),
      oneLiner = "Stay in, queue up a show.",
      summary = "22/100  ·  Rough out there"
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

  private val windMetrics: ImmutableList<MetricItemUiState> =
    persistentListOf(
      MetricItemUiState(wind(), "Wind Speed", "12 km/h"),
      MetricItemUiState(compass(), "Direction", "SW"),
      MetricItemUiState(windGusts(), "Wind Gusts", "20 km/h"),
      MetricItemUiState(windMax(), "Max Wind", "28 km/h")
    )

  private val atmosphereMetrics: ImmutableList<MetricItemUiState> =
    persistentListOf(
      MetricItemUiState(humidity(), "Humidity", "65%"),
      MetricItemUiState(gauge(), "Pressure", "1015 hPa"),
      MetricItemUiState(dewDrop(), "Dew Point", "12°"),
      MetricItemUiState(cloud(), "Cloud Cover", "45%")
    )

  private val conditionsMetrics: ImmutableList<MetricItemUiState> =
    persistentListOf(
      MetricItemUiState(precipitation(), "Precipitation", "20%"),
      MetricItemUiState(uvIndex(), "UV Index", "3.5"),
      MetricItemUiState(eye(), "Visibility", "24 km"),
      MetricItemUiState(rainfall(), "Rainfall", "0.2 mm")
    )

  val detailsSections: DetailsSectionsUiState =
    DetailsSectionsUiState(
      atmosphere = atmosphereMetrics,
      conditions = conditionsMetrics,
      previewItems = persistentListOf(
        atmosphereMetrics[0],
        windMetrics[0],
        conditionsMetrics[1],
        conditionsMetrics[0]
      ),
      wind = windMetrics
    )

  val moderateAirQualityChip: AirQualityChipUiState =
    AirQualityChipUiState(
      indicator = Emojis.aqiModerate(),
      label = "Moderate smog",
      contentDescription = "Air quality: Moderate smog, index 55",
      tint = EnvChipTint.AMBER
    )

  val highPollenChip: PollenChipUiState =
    PollenChipUiState(
      indicator = Emojis.pollen(),
      label = "High pollen",
      contentDescription = "Pollen level: High pollen, birch",
      tint = EnvChipTint.GREEN
    )

  val smogAlert: HomeAlertUiState =
    HomeAlertUiState(
      indicator = Emojis.warning(),
      title = "Smog warning",
      message = "Very poor smog — consider limiting outdoor time.",
      contentDescription = "Air quality alert: Very poor smog, index 110"
    )

  val loadedPlaylist: PlaylistUiState.Loaded =
    PlaylistUiState.Loaded(
      genres = persistentListOf(
        GenreChipUiState(name = "lo-fi hip hop"),
        GenreChipUiState(name = "acoustic"),
        GenreChipUiState(name = "rainy day indie")
      ),
      mood = "Cozy rainy afternoon",
      moodDescription = "Stay in, grab a warm drink, and let the music match the rain",
      spotifyQuery = "spotify:search:lo-fi hip hop acoustic rainy day indie",
      ytMusicUrl = "https://music.youtube.com/search?q=lo-fi+hip+hop"
    )

  val forecastSection: ForecastSectionUiState =
    ForecastSectionUiState(
      currentWeather = warmDayCurrent,
      dailyForecast = weekForecast,
      header = header,
      hourlyForecast = eightHoursForecast,
      sunriseSunset = afternoonSunInfo
    )

  val aiSuggestionSection: AiSuggestionSectionUiState =
    AiSuggestionSectionUiState(playlist = loadedPlaylist)

  val pleasantDailyVibeCard: DailyVibeCardUiState =
    DailyVibeCardUiState(
      airQualityChip = moderateAirQualityChip,
      pollenChip = highPollenChip,
      vibe = pleasantVibe
    )

  val roughDailyVibeCard: DailyVibeCardUiState =
    DailyVibeCardUiState(
      airQualityChip = moderateAirQualityChip,
      pollenChip = highPollenChip,
      vibe = roughVibe
    )
}
