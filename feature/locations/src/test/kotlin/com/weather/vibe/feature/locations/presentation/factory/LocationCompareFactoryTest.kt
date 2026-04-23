package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.location.model.FavoriteWithWeather
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_FAVORITE
import com.weather.vibe.feature.locations.presentation.fixture.FavoriteFixtures.WARSAW_SNAPSHOT
import com.weather.vibe.feature.locations.presentation.state.LocationCardUi
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class LocationCompareFactoryTest {

  private val factory = LocationCompareFactory(weatherFactory = LocationWeatherFactory())

  private val card = LocationCardUi(
    favoriteId = WARSAW_FAVORITE.id,
    feelsLikeC = 20,
    highC = 26,
    hourlyTemperatures = persistentListOf(),
    humidityPercent = 55,
    label = "Dom",
    locationId = WARSAW_FAVORITE.location.id,
    lowC = 16,
    name = WARSAW_FAVORITE.location.name,
    precipitationChancePercent = 10,
    region = "Mazowieckie",
    temperatureC = 22,
    weather = LocationWeatherUi.Sunny,
    windKph = 12
  )

  @Test
  fun `given source with snapshot, when compare created, then temperature rounded`() {

    val result = factory.create(
      card = card,
      source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = FavoriteFixtures.snapshot(temperatureC = 22.4))
    )

    expectThat(result).isNotNull().get { temperatureC }.isEqualTo(22)
  }

  @Test
  fun `given source without snapshot, when compare created, then returns null`() {

    val result = factory.create(
      card = card,
      source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = null)
    )

    expectThat(result).isNull()
  }

  @Test
  fun `given source with hourly data, when compare created, then hourly mirrored`() {

    val result = factory.create(
      card = card,
      source = FavoriteWithWeather(favorite = WARSAW_FAVORITE, snapshot = WARSAW_SNAPSHOT)
    )

    expectThat(result).isNotNull().get { hourlyTemperatures }.hasSize(WARSAW_SNAPSHOT.hourlyTemperaturesC.size)
  }
}
