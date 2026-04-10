package com.weather.vibe.domain.weather.format

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.usecase.ConvertTemperature
import org.koin.core.annotation.Factory
import kotlin.math.roundToInt

@Factory
class TemperatureFormatter internal constructor(
  private val convertTemperature: ConvertTemperature
) {

  fun format(celsius: Double, unit: TemperatureUnit): String {
    val value = convertTemperature(celsius = celsius, unit = unit)
    return "${value.roundToInt()}$DEGREE_SYMBOL"
  }

  private companion object {
    const val DEGREE_SYMBOL = "°"
  }
}
