package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
class ConvertTemperature internal constructor() {

  operator fun invoke(celsius: Double, unit: TemperatureUnit): String {
    val value = if (unit == FAHRENHEIT) toFahrenheit(celsius) else celsius
    return "${value.roundToInt()}$DEGREE_SYMBOL"
  }

  private fun toFahrenheit(celsius: Double): Double =
    celsius * FAHRENHEIT_MULTIPLIER / FAHRENHEIT_DIVISOR + FAHRENHEIT_OFFSET

  private companion object {
    const val DEGREE_SYMBOL = "\u00B0"
    const val FAHRENHEIT_DIVISOR = 5.0
    const val FAHRENHEIT_MULTIPLIER = 9.0
    const val FAHRENHEIT_OFFSET = 32.0
  }
}
