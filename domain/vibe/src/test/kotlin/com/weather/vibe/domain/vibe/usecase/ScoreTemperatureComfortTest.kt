package com.weather.vibe.domain.vibe.usecase

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScoreTemperatureComfortTest {

  private val score = ScoreTemperatureComfort()

  @Test
  fun `when apparent temperature within comfort band, then no penalty applied`() {

    expectThat(score(apparent = 22.0)).isEqualTo(0)
  }

  @Test
  fun `when apparent temperature five degrees off ideal, then mild penalty applied`() {

    expectThat(score(apparent = 25.0)).isEqualTo(5)
  }

  @Test
  fun `when apparent temperature near freezing, then harsh penalty applied`() {

    expectThat(score(apparent = 2.0)).isEqualTo(25)
  }

  @Test
  fun `when apparent temperature extreme, then maximum penalty applied`() {

    expectThat(score(apparent = 40.0)).isEqualTo(35)
  }
}
