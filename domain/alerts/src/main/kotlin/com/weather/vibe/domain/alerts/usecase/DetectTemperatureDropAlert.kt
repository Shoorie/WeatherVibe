package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.alerts.model.WeatherAlert.SharpTemperatureDrop
import com.weather.vibe.domain.weather.model.HourlyWeather
import org.koin.core.annotation.Factory
import kotlin.math.abs

@Factory
class DetectTemperatureDropAlert internal constructor() {

  operator fun invoke(forecast: List<HourlyWeather>): SharpTemperatureDrop? {

    val first = forecast.firstOrNull() ?: return null
    val coldest = forecast.minByOrNull(HourlyWeather::temperature) ?: return null
    val delta = first.temperature - coldest.temperature

    if (delta < SHARP_DROP_CELSIUS) return null

    return SharpTemperatureDrop(
      expectedAt = coldest.time,
      degreesCelsius = abs(delta)
    )
  }

  private companion object {
    const val SHARP_DROP_CELSIUS = 8.0
  }
}
