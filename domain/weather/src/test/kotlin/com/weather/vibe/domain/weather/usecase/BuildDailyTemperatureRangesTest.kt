package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.settings.model.TemperatureUnit.CELSIUS
import com.weather.vibe.domain.settings.model.TemperatureUnit.FAHRENHEIT
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.TODAY_DATE
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.dailyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.map
import java.time.LocalDate

class BuildDailyTemperatureRangesTest {

  private val buildDailyTemperatureRanges = BuildDailyTemperatureRanges(
    convertTemperature = ConvertTemperature()
  )

  private val today: LocalDate = TODAY_DATE
  private val tomorrow: LocalDate = today.plusDays(1)
  private val dayAfter: LocalDate = today.plusDays(2)

  @Test
  fun `given empty days, then return empty list`() {

    val result = buildDailyTemperatureRanges(
      days = emptyList(),
      currentTemperatureCelsius = 20.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result).isEqualTo(emptyList())
  }

  @Test
  fun `given three days, then return one range per day`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 15.0),
      dailyWeather(date = tomorrow, minTemperature = 12.0, maxTemperature = 18.0),
      dailyWeather(date = dayAfter, minTemperature = 8.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 14.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result).hasSize(3)
  }

  @Test
  fun `given decimal celsius below half, then displayed min rounds down`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.4, maxTemperature = 15.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 12.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].displayedMin).isEqualTo(10)
  }

  @Test
  fun `given decimal celsius above half, then displayed max rounds up`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 15.8)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 12.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].displayedMax).isEqualTo(16)
  }

  @Test
  fun `given fahrenheit unit and zero celsius min, then displayed min is 32`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 0.0, maxTemperature = 10.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 5.0,
      unit = FAHRENHEIT,
      today = today
    )

    expectThat(result[0].displayedMin).isEqualTo(32)
  }

  @Test
  fun `given fahrenheit unit and 100 celsius max, then displayed max is 212`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 50.0, maxTemperature = 100.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 75.0,
      unit = FAHRENHEIT,
      today = today
    )

    expectThat(result[0].displayedMax).isEqualTo(212)
  }

  @Test
  fun `given coldest day in week, then start fraction is zero`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 5.0, maxTemperature = 10.0),
      dailyWeather(date = tomorrow, minTemperature = 15.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 8.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].startFraction).isEqualTo(0f)
  }

  @Test
  fun `given warmest day in week, then end fraction is one`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 5.0, maxTemperature = 10.0),
      dailyWeather(date = tomorrow, minTemperature = 15.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 8.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[1].endFraction).isEqualTo(1f)
  }

  @Test
  fun `given today's date, then current fraction is between zero and one`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 15.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].currentFraction)
      .isNotNull()
      .isGreaterThan(0f)
      .isLessThan(1f)
  }

  @Test
  fun `given non-today date, then current fraction is null`() {

    val days = listOf(
      dailyWeather(date = tomorrow, minTemperature = 10.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 15.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].currentFraction).isNull()
  }

  @Test
  fun `given current temperature below week min, then current fraction equals start fraction`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = -5.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].currentFraction).isEqualTo(result[0].startFraction)
  }

  @Test
  fun `given current temperature above week max, then current fraction equals end fraction`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 99.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].currentFraction).isEqualTo(result[0].endFraction)
  }

  @Test
  fun `given single day with equal min and max, then start fraction is zero`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 15.0, maxTemperature = 15.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 15.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].startFraction).isEqualTo(0f)
  }

  @Test
  fun `given single day with equal min and max, then end fraction is zero`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 15.0, maxTemperature = 15.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 15.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result[0].endFraction).isEqualTo(0f)
  }

  @Test
  fun `given three days, then dates match input order`() {

    val days = listOf(
      dailyWeather(date = today, minTemperature = 10.0, maxTemperature = 15.0),
      dailyWeather(date = tomorrow, minTemperature = 12.0, maxTemperature = 18.0),
      dailyWeather(date = dayAfter, minTemperature = 8.0, maxTemperature = 20.0)
    )

    val result = buildDailyTemperatureRanges(
      days = days,
      currentTemperatureCelsius = 14.0,
      unit = CELSIUS,
      today = today
    )

    expectThat(result).map { it.date }
      .containsExactly(today, tomorrow, dayAfter)
  }
}
