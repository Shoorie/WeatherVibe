package com.weather.vibe.feature.locations.presentation.factory

import com.weather.vibe.domain.weather.model.SimplifiedCondition.CLOUDY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.FOGGY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.RAINY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SNOWY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.STORMY
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.feature.locations.presentation.fixture.LocationFavoriteFixtures
import com.weather.vibe.feature.locations.presentation.state.LocationWeatherUi
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class LocationWeatherFactoryTest {

  private val factory = LocationWeatherFactory()

  @Test
  fun `given night snapshot, when created, then returns night`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(isDay = false))

    expectThat(result).isEqualTo(LocationWeatherUi.Night)
  }

  @Test
  fun `given sunny day snapshot, when created, then returns sunny`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = SUNNY))

    expectThat(result).isEqualTo(LocationWeatherUi.Sunny)
  }

  @Test
  fun `given cloudy day snapshot, when created, then returns partly cloudy`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = CLOUDY))

    expectThat(result).isEqualTo(LocationWeatherUi.PartlyCloudy)
  }

  @Test
  fun `given rainy day snapshot, when created, then returns rain`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = RAINY))

    expectThat(result).isEqualTo(LocationWeatherUi.Rain)
  }

  @Test
  fun `given snowy day snapshot, when created, then returns snow`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = SNOWY))

    expectThat(result).isEqualTo(LocationWeatherUi.Snow)
  }

  @Test
  fun `given stormy day snapshot, when created, then returns rain`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = STORMY))

    expectThat(result).isEqualTo(LocationWeatherUi.Rain)
  }

  @Test
  fun `given foggy day snapshot, when created, then returns cloudy`() {

    val result = factory.create(snapshot = LocationFavoriteFixtures.snapshot(condition = FOGGY))

    expectThat(result).isEqualTo(LocationWeatherUi.Cloudy)
  }
}
