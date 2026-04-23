package com.weather.vibe.feature.locations.preview

import com.weather.vibe.domain.location.model.LocationWeatherAdvantage
import com.weather.vibe.domain.location.model.LocationWeatherComparison
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.presentation.state.LocationCompareUiState
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import com.weather.vibe.feature.locations.presentation.state.LocationsUiState.Loaded
import com.weather.vibe.feature.locations.presentation.state.TemperatureAxisUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

internal object LocationsPreviewData {

  val warsaw = LocationCardUiState(
    favoriteId = 1L,
    feelsLike = "8°",
    high = "14°",
    hourlyTemperatures = sampleHourlyCurve(seed = 1, lowC = 3, highC = 14),
    humidityPercent = 74,
    label = "Home",
    locationId = 101L,
    low = "3°",
    name = "Warsaw",
    precipitationChancePercent = 45,
    region = "Mazowieckie",
    temperature = "10°",
    weather = LocationWeatherUi.Cloudy,
    windKph = 9
  )

  val wroclaw = LocationCardUiState(
    favoriteId = 2L,
    feelsLike = "10°",
    high = "15°",
    hourlyTemperatures = sampleHourlyCurve(seed = 2, lowC = 4, highC = 15),
    humidityPercent = 71,
    label = "Work",
    locationId = 102L,
    low = "4°",
    name = "Wroclaw",
    precipitationChancePercent = 30,
    region = "Dolnośląskie",
    temperature = "12°",
    weather = LocationWeatherUi.PartlyCloudy,
    windKph = 8
  )

  val zakopane = LocationCardUiState(
    favoriteId = 3L,
    feelsLike = "-4°",
    high = "2°",
    hourlyTemperatures = sampleHourlyCurve(seed = 3, lowC = -6, highC = 2),
    humidityPercent = 82,
    label = "Vacation",
    locationId = 103L,
    low = "-6°",
    name = "Zakopane",
    precipitationChancePercent = 65,
    region = "Małopolskie",
    temperature = "-1°",
    weather = LocationWeatherUi.Snow,
    windKph = 18
  )

  val madrid = LocationCardUiState(
    favoriteId = 4L,
    feelsLike = "22°",
    high = "26°",
    hourlyTemperatures = sampleHourlyCurve(seed = 4, lowC = 16, highC = 26),
    humidityPercent = 48,
    label = "Family",
    locationId = 104L,
    low = "16°",
    name = "Madrid",
    precipitationChancePercent = 10,
    region = "Spain",
    temperature = "23°",
    weather = LocationWeatherUi.Sunny,
    windKph = 11
  )

  val defaultCards = persistentListOf(warsaw, wroclaw, zakopane, madrid)

  val warsawCompare = LocationCompareUiState(
    card = warsaw,
    feelsLike = warsaw.feelsLike.orEmpty(),
    high = warsaw.high.orEmpty(),
    hourlyTemperatures = warsaw.hourlyTemperatures,
    humidityPercent = warsaw.humidityPercent ?: 0,
    low = warsaw.low.orEmpty(),
    precipitationChancePercent = warsaw.precipitationChancePercent ?: 0,
    temperature = warsaw.temperature.orEmpty(),
    weather = warsaw.weather ?: LocationWeatherUi.Cloudy,
    windKph = warsaw.windKph ?: 0
  )

  val madridCompare = LocationCompareUiState(
    card = madrid,
    feelsLike = madrid.feelsLike.orEmpty(),
    high = madrid.high.orEmpty(),
    hourlyTemperatures = madrid.hourlyTemperatures,
    humidityPercent = madrid.humidityPercent ?: 0,
    low = madrid.low.orEmpty(),
    precipitationChancePercent = madrid.precipitationChancePercent ?: 0,
    temperature = madrid.temperature.orEmpty(),
    weather = madrid.weather ?: LocationWeatherUi.Sunny,
    windKph = madrid.windKph ?: 0
  )

  val comparePair = LocationComparePairUiState(
    first = warsawCompare,
    second = madridCompare,
    winners = LocationWeatherComparison(
      temperature = LocationWeatherAdvantage.SecondLocation,
      wind = LocationWeatherAdvantage.FirstLocation,
      humidity = LocationWeatherAdvantage.SecondLocation,
      rain = LocationWeatherAdvantage.SecondLocation
    ),
    temperatureAxis = TemperatureAxisUiState(
      min = "0°",
      mid = "13°",
      max = "26°"
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
    selectedIds = persistentSetOf(1L, 4L)
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
