package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.LocationFavoriteWithWeather
import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.feature.locations.presentation.fake.fakeTemperatureFormatter
import com.weather.vibe.feature.locations.presentation.fixture.LocationCardUiStateFixtures.WARSAW_CARD
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures.snapshot
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

  @Test
  fun `given source with snapshot, when compare created, then temperature is formatted`() {

    val result = factory.create(
      card = WARSAW_CARD,
      source = LocationFavoriteWithWeather(
        favorite = WARSAW_FAVORITE,
        snapshot = snapshot(temperatureC = 22.4)
      ),
      temperatureUnit = CELSIUS
    )

    expectThat(result)
      .isNotNull()
      .get { temperature }.isEqualTo("22°")
  }

  @Test
  fun `given source without snapshot, when compare created, then result is null`() {

    val result = factory.create(
      card = WARSAW_CARD,
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null),
      temperatureUnit = CELSIUS
    )

    expectThat(result).isNull()
  }

  @Test
  fun `given source with hourly data, when compare created, then hourly size is preserved`() {

    val result = factory.create(
      card = WARSAW_CARD,
      source = LocationFavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT),
      temperatureUnit = CELSIUS
    )

    expectThat(result)
      .isNotNull()
      .get { hourlyTemperatures }.hasSize(WARSAW_SNAPSHOT.hourlyTemperaturesC.size)
  }
}
