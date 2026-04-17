package com.weather.vibe.domain.vibe.usecase

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScoreUvBurdenTest {

  private val score = ScoreUvBurden()

  @Test
  fun `when uv index low, then no penalty applied`() {

    expectThat(score(uvIndex = 2.0)).isEqualTo(0)
  }

  @Test
  fun `when uv index moderate, then no penalty applied`() {

    expectThat(score(uvIndex = 4.0)).isEqualTo(0)
  }

  @Test
  fun `when uv index reaches high, then mild penalty applied`() {

    expectThat(score(uvIndex = 7.0)).isEqualTo(5)
  }

  @Test
  fun `when uv index reaches very high, then moderate penalty applied`() {

    expectThat(score(uvIndex = 9.0)).isEqualTo(10)
  }

  @Test
  fun `when uv index reaches extreme, then harsh penalty applied`() {

    expectThat(score(uvIndex = 12.0)).isEqualTo(15)
  }
}
