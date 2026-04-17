package com.weather.vibe.domain.vibe.usecase

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScoreWindComfortTest {

  private val score = ScoreWindComfort()

  @Test
  fun `when wind calm, then no penalty applied`() {

    expectThat(score(kmh = 10.0)).isEqualTo(0)
  }

  @Test
  fun `when wind stiff breeze, then mild penalty applied`() {

    expectThat(score(kmh = 25.0)).isEqualTo(5)
  }

  @Test
  fun `when wind reaches gale, then harsh penalty applied`() {

    expectThat(score(kmh = 70.0)).isEqualTo(15)
  }
}
