package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit
import com.weather.vibe.domain.weather.model.DailyTemperatureRange
import com.weather.vibe.domain.weather.model.DailyWeather
import org.koin.core.annotation.Factory
import java.time.LocalDate
import kotlin.math.roundToInt

@Factory
class BuildDailyTemperatureRanges internal constructor(
  private val convertTemperature: ConvertTemperature
) {

  operator fun invoke(
    days: List<DailyWeather>,
    currentTemperatureCelsius: Double,
    unit: TemperatureUnit,
    today: LocalDate
  ): List<DailyTemperatureRange> {

    if (days.isEmpty()) return emptyList()

    val minima = days.map { roundToUnit(it.minTemperature, unit) }
    val maxima = days.map { roundToUnit(it.maxTemperature, unit) }
    val weekMin = minima.min()
    val totalRange = (maxima.max() - weekMin).coerceAtLeast(MIN_RANGE).toFloat()
    val currentRounded = roundToUnit(currentTemperatureCelsius, unit)

    return days.mapIndexed { index, day ->
      buildRange(
        day = day,
        displayedMin = minima[index],
        displayedMax = maxima[index],
        currentRounded = currentRounded,
        weekMin = weekMin,
        totalRange = totalRange,
        today = today
      )
    }
  }

  private fun roundToUnit(celsius: Double, unit: TemperatureUnit): Int =
    convertTemperature(celsius = celsius, unit = unit).roundToInt()

  private fun buildRange(
    day: DailyWeather,
    displayedMin: Int,
    displayedMax: Int,
    currentRounded: Int,
    weekMin: Int,
    totalRange: Float,
    today: LocalDate
  ): DailyTemperatureRange {

    val start = fractionOf(displayedMin, weekMin, totalRange)
    val end = fractionOf(displayedMax, weekMin, totalRange)
    val current = currentRounded
      .takeIf { day.date == today }
      ?.let { fractionOf(it, weekMin, totalRange).coerceIn(start, end) }

    return DailyTemperatureRange(
      date = day.date,
      displayedMin = displayedMin,
      displayedMax = displayedMax,
      startFraction = start,
      endFraction = end,
      currentFraction = current
    )
  }

  private fun fractionOf(value: Int, origin: Int, range: Float): Float =
    ((value - origin) / range).coerceIn(0f, 1f)

  private companion object {
    const val MIN_RANGE = 1
  }
}
