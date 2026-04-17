package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.testing.airquality.fixture.PollenFixtures.CALM
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.HIGH_BIRCH
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScorePollenBurdenTest {

  private val score = ScorePollenBurden()

  @Test
  fun `when pollen reading missing, then no penalty applied`() {

    expectThat(score(pollen = null)).isEqualTo(0)
  }

  @Test
  fun `when all species stay low, then no penalty applied`() {

    expectThat(score(pollen = CALM)).isEqualTo(0)
  }

  @Test
  fun `when species reaches high level, then moderate penalty applied`() {

    expectThat(score(pollen = HIGH_BIRCH)).isEqualTo(10)
  }
}
