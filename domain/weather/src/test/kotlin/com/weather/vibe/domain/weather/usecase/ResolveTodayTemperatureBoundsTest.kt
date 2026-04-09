package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.dailyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import com.weather.vibe.domain.weather.model.TemperatureBounds
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ResolveTodayTemperatureBoundsTest {

  private val resolveTodayTemperatureBounds = ResolveTodayTemperatureBounds()

  @Test
  fun `given daily forecast, when resolved, then max comes from first day`() {

    val result = resolveTodayTemperatureBounds(weatherData())

    expectThat(result.max).isEqualTo(25.0)
  }

  @Test
  fun `given daily forecast, when resolved, then min comes from first day`() {

    val result = resolveTodayTemperatureBounds(weatherData())

    expectThat(result.min).isEqualTo(12.0)
  }

  @Test
  fun `given multiple days, when resolved, then only first day is used`() {

    val weather = weatherData(
      dailyForecast = listOf(
        dailyWeather(minTemperature = 12.0, maxTemperature = 25.0),
        dailyWeather(minTemperature = 5.0, maxTemperature = 30.0)
      )
    )
    val result = resolveTodayTemperatureBounds(weather)

    expectThat(result).isEqualTo(TemperatureBounds(min = 12.0, max = 25.0))
  }

  @Test
  fun `given empty forecast, when resolved, then max falls back to current temperature`() {

    val weather = weatherData(dailyForecast = emptyList())
    val result = resolveTodayTemperatureBounds(weather)

    expectThat(result.max).isEqualTo(22.0)
  }

  @Test
  fun `given empty forecast, when resolved, then min falls back to current temperature`() {

    val weather = weatherData(dailyForecast = emptyList())
    val result = resolveTodayTemperatureBounds(weather)

    expectThat(result.min).isEqualTo(22.0)
  }
}
