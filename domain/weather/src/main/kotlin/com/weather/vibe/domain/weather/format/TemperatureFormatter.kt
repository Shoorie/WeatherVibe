package com.weather.vibe.domain.weather.format

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.usecase.ConvertTemperature
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
class TemperatureFormatter internal constructor(
  private val convertTemperature: ConvertTemperature
) {

  fun format(celsius: Double, unit: TemperatureUnit): String =
    "${roundedValue(celsius = celsius, unit = unit)}$DEGREE_SYMBOL"

  fun roundedValue(celsius: Double, unit: TemperatureUnit): Int =
    convertTemperature(celsius = celsius, unit = unit).roundToInt()

  private companion object {
    const val DEGREE_SYMBOL = "°"
  }
}
