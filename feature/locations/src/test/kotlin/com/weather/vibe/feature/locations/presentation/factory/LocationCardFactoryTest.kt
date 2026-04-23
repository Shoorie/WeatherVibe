package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.DEFAULT_LABEL
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.KRAKOW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.favorite
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.snapshot
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi.Sunny
import com.weather.vibe.testing.location.fixture.LocationFixtures.ADMIN1
import com.weather.vibe.testing.location.fixture.LocationFixtures.COUNTRY
import com.weather.vibe.testing.location.fixture.LocationFixtures.location
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class LocationCardFactoryTest {

  private val temperatureFormatter = fakeTemperatureFormatter()
  private val factory = LocationCardFactory(
    temperatureFormatter = temperatureFormatter,
    weatherFactory = LocationWeatherFactory()
  )

  @Test
  fun `given snapshot temperature 22_6, when card created, then temperature rounds to 23`() {

    val source = LocationFavoriteWithWeather(
      favorite = WARSAW_FAVORITE,
      snapshot = snapshot(temperatureC = 22.6)
    )

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.temperature).isEqualTo("23°")
  }

  @Test
  fun `given missing snapshot, when card created, then temperature is null`() {

    val source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.temperature).isNull()
  }

  @Test
  fun `given missing snapshot, when card created, then weather is null`() {

    val source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.weather).isNull()
  }

  @Test
  fun `given favorite with custom label, when card created, then label is preserved`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT),
      temperatureUnit = CELSIUS
    )

    expectThat(result.label).isEqualTo(DEFAULT_LABEL)
  }

  @Test
  fun `given favorite without label, when card created, then label is null`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = KRAKOW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.label).isNull()
  }

  @Test
  fun `given favorite with admin1, when card created, then region comes from admin1`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.region).isEqualTo(ADMIN1)
  }

  @Test
  fun `given favorite without admin1, when card created, then region falls back to country`() {

    val favorite = favorite(location = location(admin1 = null))

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = favorite, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.region).isEqualTo(COUNTRY)
  }

  @Test
  fun `given sunny day snapshot, when card created, then weather is sunny`() {

    val source = LocationFavoriteWithWeather(
      favorite = WARSAW_FAVORITE,
      snapshot = WARSAW_SNAPSHOT
    )

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.weather).isEqualTo(Sunny)
  }
}
