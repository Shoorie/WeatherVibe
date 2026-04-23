package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.KRAKOW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import com.weather.vibe.testing.location.fixture.LocationFixtures
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
  fun `snapshot with temperature rounds and formats value`() {

    val source = LocationFavoriteWithWeather(
      favorite = WARSAW_FAVORITE,
      snapshot = LocationFavoriteFixtures.snapshot(temperatureC = 22.6)
    )

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.temperature).isEqualTo("23°")
  }

  @Test
  fun `missing snapshot leaves temperature and weather null`() {

    val source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.temperature).isNull()
    expectThat(result.weather).isNull()
  }

  @Test
  fun `favorite with custom label keeps the label on the card`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT),
      temperatureUnit = CELSIUS
    )

    expectThat(result.label).isEqualTo(LocationFavoriteFixtures.DEFAULT_LABEL)
  }

  @Test
  fun `favorite without label leaves card label null`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = KRAKOW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.label).isNull()
  }

  @Test
  fun `favorite with admin1 fills region from admin1`() {

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.region).isEqualTo(LocationFixtures.ADMIN1)
  }

  @Test
  fun `favorite without admin1 falls back to country`() {

    val favorite = LocationFavoriteFixtures.favorite(
      location = LocationFixtures.location(admin1 = null)
    )

    val result = factory.create(
      source = LocationFavoriteWithWeather(favorite = favorite, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result.region).isEqualTo(LocationFixtures.COUNTRY)
  }

  @Test
  fun `sunny day snapshot yields sunny weather`() {

    val source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT)

    val result = factory.create(source = source, temperatureUnit = CELSIUS)

    expectThat(result.weather).isEqualTo(LocationWeatherUi.Sunny)
  }
}
