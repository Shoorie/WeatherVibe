package com.weather.vibe.domain.weather.model

import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WeatherConditionTest {

  @Test
  fun `given clear sky at night, then emoji is moon`() {

    expectThat(CLEAR_SKY.emojiAt(isDay = false)).isEqualTo("🌙")
  }

  @Test
  fun `given clear sky at day, then emoji is sun`() {

    expectThat(CLEAR_SKY.emojiAt(isDay = true)).isEqualTo(CLEAR_SKY.emoji)
  }

  @Test
  fun `given rain at night, then emoji stays as rain`() {

    expectThat(RAIN.emojiAt(isDay = false)).isEqualTo(RAIN.emoji)
  }

  @Test
  fun `given partly cloudy at night, then emoji stays as partly cloudy`() {

    expectThat(PARTLY_CLOUDY.emojiAt(isDay = false)).isEqualTo(PARTLY_CLOUDY.emoji)
  }
}
