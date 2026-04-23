package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.KRAKOW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import com.weather.vibe.testing.location.fixture.LocationFixtures
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class LocationCardFactoryTest {

  private val factory = LocationCardFactory(weatherFactory = LocationWeatherFactory())

  @Test
  fun `given snapshot with temperature, when card created, then temperature rounded`() {

    val source = FavoriteWithWeather(
      favorite = WARSAW_FAVORITE,
      snapshot = FavoriteFixtures.snapshot(temperatureC = 22.6)
    )

    val result = factory.create(source = source)

    expectThat(result.temperatureC).isEqualTo(23)
  }

  @Test
  fun `given no snapshot, when card created, then temperature and weather are null`() {

    val source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)

    val result = factory.create(source = source)

    expectThat(result.temperatureC).isNull()
    expectThat(result.weather).isNull()
  }

  @Test
  fun `given favorite with custom label, when card created, then label is preserved`() {

    val result = factory.create(source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT))

    expectThat(result.label).isEqualTo(FavoriteFixtures.DEFAULT_LABEL)
  }

  @Test
  fun `given favorite without label, when card created, then label is null`() {

    val result = factory.create(source = FavoriteWithWeather(favorite = KRAKOW_FAVORITE, snapshot = null))

    expectThat(result.label).isNull()
  }

  @Test
  fun `given favorite with admin1, when card created, then region uses admin1`() {

    val result = factory.create(source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null))

    expectThat(result.region).isEqualTo(LocationFixtures.ADMIN1)
  }

  @Test
  fun `given favorite without admin1, when card created, then region falls back to country`() {

    val favorite = FavoriteFixtures.favorite(
      location = LocationFixtures.location(admin1 = null)
    )

    val result = factory.create(source = FavoriteWithWeather(favorite = favorite, snapshot = null))

    expectThat(result.region).isEqualTo(LocationFixtures.COUNTRY)
  }

  @Test
  fun `given sunny day snapshot, when card created, then weather is sunny`() {

    val source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT)

    val result = factory.create(source = source)

    expectThat(result.weather).isEqualTo(LocationWeatherUi.Sunny)
  }
}
