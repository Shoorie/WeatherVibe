package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.state.LocationCardUiState
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class LocationCompareFactoryTest {

  private val temperatureFormatter = fakeTemperatureFormatter()
  private val factory = LocationCompareFactory(
    temperatureFormatter = temperatureFormatter,
    weatherFactory = LocationWeatherFactory()
  )

  private val card = LocationCardUiState(
    favoriteId = WARSAW_FAVORITE.id,
    feelsLike = "20°",
    high = "26°",
    hourlyTemperatures = persistentListOf(),
    humidityPercent = 55,
    label = "Dom",
    locationId = WARSAW_FAVORITE.location.id,
    low = "16°",
    name = WARSAW_FAVORITE.location.name,
    precipitationChancePercent = 10,
    region = "Mazowieckie",
    temperature = "22°",
    weather = LocationWeatherUi.Sunny,
    windKph = 12
  )

  @Test
  fun `source with snapshot produces compare with formatted temperature`() {

    val result = factory.create(
      card = card,
      source = LocationFavoriteWithWeather(
        favorite = WARSAW_FAVORITE,
        snapshot = LocationFavoriteFixtures.snapshot(temperatureC = 22.4)
      ),
      temperatureUnit = CELSIUS
    )

    expectThat(result)
      .isNotNull()
      .get { temperature }
      .isEqualTo("22°")
  }

  @Test
  fun `source without snapshot produces null compare`() {

    val result = factory.create(
      card = card,
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result).isNull()
  }

  @Test
  fun `source with hourly data mirrors size on compare`() {

    val result = factory.create(
      card = card,
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT),
      temperatureUnit = CELSIUS
    )

    expectThat(result)
      .isNotNull()
      .get { hourlyTemperatures }
      .hasSize(WARSAW_SNAPSHOT.hourlyTemperaturesC.size)
  }
}
