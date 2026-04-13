package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ConvertTemperatureTest {

  private val convertTemperature = ConvertTemperature()

  @Test
  fun `given celsius unit, then value unchanged`() {

    val result = convertTemperature(celsius = 20.0, unit = CELSIUS)

    expectThat(result).isEqualTo(20.0)
  }

  @Test
  fun `given fahrenheit unit and zero celsius, then return 32`() {

    val result = convertTemperature(celsius = 0.0, unit = FAHRENHEIT)

    expectThat(result).isEqualTo(32.0)
  }

  @Test
  fun `given fahrenheit unit and 100 celsius, then return 212`() {

    val result = convertTemperature(celsius = 100.0, unit = FAHRENHEIT)

    expectThat(result).isEqualTo(212.0)
  }

  @Test
  fun `given fahrenheit unit and minus 40 celsius, then return minus 40`() {

    val result = convertTemperature(celsius = -40.0, unit = FAHRENHEIT)

    expectThat(result).isEqualTo(-40.0)
  }
}
