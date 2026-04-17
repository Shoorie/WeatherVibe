package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScoreRainOutlookTest {

  private val score = ScoreRainOutlook()

  @Test
  fun `when hourly forecast empty, then no penalty applied`() {

    expectThat(score(hourly = emptyList())).isEqualTo(0)
  }

  @Test
  fun `when next hours stay dry, then minimal penalty applied`() {

    val hourly = List(size = 6) { hourlyWeather(precipitationProbability = 10) }

    expectThat(score(hourly = hourly)).isEqualTo(6)
  }

  @Test
  fun `when heavy rain nearly certain, then penalty capped at maximum`() {

    val hourly = List(size = 6) { hourlyWeather(precipitationProbability = 90) }

    expectThat(score(hourly = hourly)).isEqualTo(40)
  }
}
