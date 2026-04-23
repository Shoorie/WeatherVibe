package com.weather.vibe.feature.locations.preview

import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import com.weather.vibe.domain.location.model.WeatherAdvantage
import com.weather.vibe.domain.location.model.WeatherComparison
import com.weather.vibe.feature.locations.presentation.state.LocationComparePair
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUi
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

internal object LocationsPreviewData {

  val warsaw = card(
    favoriteId = 1L,
    locationId = 101L,
    name = "Warszawa",
    region = "mazowieckie",
    weather = LocationWeatherUi.Cloudy,
    temperatureC = 10,
    feelsLikeC = 8,
    highC = 14,
    lowC = 3,
    humidityPercent = 74,
    precipitationChancePercent = 45,
    windKph = 9,
    label = "Dom"
  )

  val wroclaw = card(
    favoriteId = 2L,
    locationId = 102L,
    name = "Wrocław",
    region = "dolnośląskie",
    weather = LocationWeatherUi.PartlyCloudy,
    temperatureC = 12,
    feelsLikeC = 10,
    highC = 15,
    lowC = 4,
    humidityPercent = 71,
    precipitationChancePercent = 30,
    windKph = 8,
    label = "Praca"
  )

  val zakopane = card(
    favoriteId = 3L,
    locationId = 103L,
    name = "Zakopane",
    region = "małopolskie",
    weather = LocationWeatherUi.Snow,
    temperatureC = -1,
    feelsLikeC = -4,
    highC = 2,
    lowC = -6,
    humidityPercent = 82,
    precipitationChancePercent = 65,
    windKph = 18,
    label = "Wakacje"
  )

  val madrid = card(
    favoriteId = 4L,
    locationId = 104L,
    name = "Madryt",
    region = "Hiszpania",
    weather = LocationWeatherUi.Sunny,
    temperatureC = 23,
    feelsLikeC = 22,
    highC = 26,
    lowC = 16,
    humidityPercent = 48,
    precipitationChancePercent = 10,
    windKph = 11,
    label = "Rodzina"
  )

  val defaultCards = persistentListOf(warsaw, wroclaw, zakopane, madrid)

  val warsawCompare = compareFor(card = warsaw)
  val madridCompare = compareFor(card = madrid)

  val comparePair = LocationComparePair(
    first = warsawCompare,
    second = madridCompare,
    winners = WeatherComparison(
      temperature = WeatherAdvantage.SecondLocation,
      wind = WeatherAdvantage.FirstLocation,
      humidity = WeatherAdvantage.SecondLocation,
      rain = WeatherAdvantage.SecondLocation
    )
  )

  val browseLoaded = Loaded(
    cards = defaultCards,
    comparePair = null,
    compareMode = false,
    isRefreshing = false,
    selectedIds = persistentSetOf()
  )

  val comparingLoaded = Loaded(
    cards = defaultCards,
    comparePair = comparePair,
    compareMode = true,
    isRefreshing = false,
    selectedIds = persistentSetOf("1", "4")
  )

  @Suppress("LongParameterList")
  private fun card(
    favoriteId: Long,
    locationId: Long,
    name: String,
    region: String,
    weather: LocationWeatherUi,
    temperatureC: Int,
    feelsLikeC: Int,
    highC: Int,
    lowC: Int,
    humidityPercent: Int,
    precipitationChancePercent: Int,
    windKph: Int,
    label: String?
  ): LocationCardUi = LocationCardUi(
    favoriteId = favoriteId,
    feelsLikeC = feelsLikeC,
    highC = highC,
    hourlyTemperatures = sampleHourlyCurve(seed = favoriteId.toInt(), lowC = lowC, highC = highC),
    humidityPercent = humidityPercent,
    label = label,
    locationId = locationId,
    lowC = lowC,
    name = name,
    precipitationChancePercent = precipitationChancePercent,
    region = region,
    temperatureC = temperatureC,
    weather = weather,
    windKph = windKph
  )

  private fun compareFor(card: LocationCardUi): LocationCompareUi = LocationCompareUi(
    card = card,
    feelsLikeC = card.feelsLikeC ?: 0,
    highC = card.highC ?: 0,
    hourlyTemperatures = card.hourlyTemperatures,
    humidityPercent = card.humidityPercent ?: 0,
    lowC = card.lowC ?: 0,
    precipitationChancePercent = card.precipitationChancePercent ?: 0,
    temperatureC = card.temperatureC ?: 0,
    weather = card.weather ?: LocationWeatherUi.Cloudy,
    windKph = card.windKph ?: 0
  )

  private fun sampleHourlyCurve(
    seed: Int,
    lowC: Int,
    highC: Int
  ): ImmutableList<Float> = (0 until HOURLY_POINTS)
    .map { hour ->
      val progress = hour.toFloat() / (HOURLY_POINTS - 1).toFloat()
      val base = sin((progress - 0.25f) * PI.toFloat() * 2f) * 0.5f + 0.5f
      val noise = sin(seed + hour * 1.3f) * 0.06f
      (lowC + (highC - lowC) * (base.pow(1.2f) + noise))
    }
    .toImmutableList()

  private const val HOURLY_POINTS = 24
}
