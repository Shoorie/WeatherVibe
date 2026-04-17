package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.weather.model.UvLevel.EXTREME
import com.weather.vibe.domain.weather.model.UvLevel.HIGH
import com.weather.vibe.domain.weather.model.UvLevel.LOW
import com.weather.vibe.domain.weather.model.UvLevel.MODERATE
import com.weather.vibe.domain.weather.model.UvLevel.VERY_HIGH
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class UvLevelTest {

  @Test
  fun `when uv index below three, then level is low`() {

    expectThat(UvLevel.from(uvIndex = 2.0)).isEqualTo(LOW)
  }

  @Test
  fun `when uv index between three and five, then level is moderate`() {

    expectThat(UvLevel.from(uvIndex = 4.0)).isEqualTo(MODERATE)
  }

  @Test
  fun `when uv index reaches six, then level is high`() {

    expectThat(UvLevel.from(uvIndex = 7.0)).isEqualTo(HIGH)
  }

  @Test
  fun `when uv index reaches eight, then level is very high`() {

    expectThat(UvLevel.from(uvIndex = 9.5)).isEqualTo(VERY_HIGH)
  }

  @Test
  fun `when uv index reaches eleven, then level is extreme`() {

    expectThat(UvLevel.from(uvIndex = 12.0)).isEqualTo(EXTREME)
  }
}
