package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.TemperatureComfort.CHILLY
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort.COLD
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort.COMFY
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort.HOT
import com.weather.vibe.domain.activityplanner.model.TemperatureComfort.WARM
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ClassifyTemperatureComfortTest {

  private val classify = ClassifyTemperatureComfort()

  @Test
  fun `given sub zero, then cold returned`() {
    expectThat(classify(celsius = -2.0)).isEqualTo(COLD)
  }

  @Test
  fun `given single digit positive, then chilly returned`() {
    expectThat(classify(celsius = 8.0)).isEqualTo(CHILLY)
  }

  @Test
  fun `given mild temperature, then comfy returned`() {
    expectThat(classify(celsius = 18.0)).isEqualTo(COMFY)
  }

  @Test
  fun `given high twenties, then warm returned`() {
    expectThat(classify(celsius = 26.0)).isEqualTo(WARM)
  }

  @Test
  fun `given hot day, then hot returned`() {
    expectThat(classify(celsius = 32.0)).isEqualTo(HOT)
  }
}
