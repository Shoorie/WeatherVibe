package com.weather.vibe.feature.locations.presentation.fixture

import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import kotlinx.collections.immutable.persistentListOf

internal object LocationCardUiStateFixtures {

  const val FEELS_LIKE = "20°"
  const val HIGH = "26°"
  const val LOW = "16°"
  const val TEMPERATURE = "22°"
  const val HUMIDITY = 55
  const val PRECIPITATION = 10
  const val WIND_KPH = 12
  const val LABEL = "Home"
  const val REGION = "Mazovia"

  val WARSAW_CARD: LocationCardUiState = card()

  fun card(
    favoriteId: Long = WARSAW_FAVORITE.id,
    feelsLike: String? = FEELS_LIKE,
    high: String? = HIGH,
    humidityPercent: Int? = HUMIDITY,
    label: String? = LABEL,
    locationId: Long = WARSAW_FAVORITE.location.id,
    low: String? = LOW,
    name: String = WARSAW_FAVORITE.location.name,
    precipitationChancePercent: Int? = PRECIPITATION,
    region: String = REGION,
    temperature: String? = TEMPERATURE,
    weather: LocationWeatherUi? = LocationWeatherUi.Sunny,
    windKph: Int? = WIND_KPH
  ): LocationCardUiState = LocationCardUiState(
    favoriteId = favoriteId,
    feelsLike = feelsLike,
    high = high,
    hourlyTemperatures = persistentListOf(),
    humidityPercent = humidityPercent,
    label = label,
    locationId = locationId,
    low = low,
    name = name,
    precipitationChancePercent = precipitationChancePercent,
    region = region,
    temperature = temperature,
    weather = weather,
    windKph = windKph
  )
}
