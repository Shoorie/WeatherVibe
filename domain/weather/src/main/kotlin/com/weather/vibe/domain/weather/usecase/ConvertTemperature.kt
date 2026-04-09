package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import org.koin.core.annotation.Factory

@Factory
class ConvertTemperature internal constructor() {

  operator fun invoke(celsius: Double, unit: TemperatureUnit): Double =
    if (unit == FAHRENHEIT) toFahrenheit(celsius) else celsius

  private fun toFahrenheit(celsius: Double): Double =
    celsius * FAHRENHEIT_MULTIPLIER / FAHRENHEIT_DIVISOR + FAHRENHEIT_OFFSET

  private companion object {
    const val FAHRENHEIT_DIVISOR = 5.0
    const val FAHRENHEIT_MULTIPLIER = 9.0
    const val FAHRENHEIT_OFFSET = 32.0
  }
}
