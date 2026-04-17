package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScoreAqiBurdenTest {

  private val score = ScoreAqiBurden()

  @Test
  fun `when aqi level missing, then no penalty applied`() {

    expectThat(score(level = null)).isEqualTo(0)
  }

  @Test
  fun `when aqi level good, then no penalty applied`() {

    expectThat(score(level = GOOD)).isEqualTo(0)
  }

  @Test
  fun `when aqi level poor, then moderate penalty applied`() {

    expectThat(score(level = POOR)).isEqualTo(25)
  }

  @Test
  fun `when aqi level extremely poor, then maximum penalty applied`() {

    expectThat(score(level = EXTREMELY_POOR)).isEqualTo(40)
  }
}
