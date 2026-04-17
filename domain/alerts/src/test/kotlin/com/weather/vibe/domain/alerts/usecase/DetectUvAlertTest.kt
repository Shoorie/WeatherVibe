package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.weather.model.UvLevel.EXTREME
import com.weather.vibe.domain.weather.model.UvLevel.HIGH
import com.weather.vibe.domain.weather.model.UvLevel.VERY_HIGH
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.dailyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class DetectUvAlertTest {

  private val detect = DetectUvAlert()

  @Test
  fun `when uv index below high threshold, then no alert returned`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = MODERATE_UV)))

    expectThat(detect(weather)).isNull()
  }

  @Test
  fun `when uv index reaches high level, then alert carries high level`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = HIGH_UV)))

    expectThat(detect(weather)).isNotNull().get { level }.isEqualTo(HIGH)
  }

  @Test
  fun `when uv index reaches very high level, then alert carries very high level`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = VERY_HIGH_UV)))

    expectThat(detect(weather)).isNotNull().get { level }.isEqualTo(VERY_HIGH)
  }

  @Test
  fun `when uv index reaches extreme level, then alert carries extreme level`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = EXTREME_UV)))

    expectThat(detect(weather)).isNotNull().get { level }.isEqualTo(EXTREME)
  }

  @Test
  fun `when uv alert raised, then uv index rounded to nearest integer`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = HIGH_UV)))

    expectThat(detect(weather)).isNotNull().get { uvIndex }.isEqualTo(HIGH_UV.toInt())
  }

  @Test
  fun `when daily forecast empty, then no alert returned`() {

    val weather = weatherData(dailyForecast = emptyList())

    expectThat(detect(weather)).isNull()
  }

  private companion object {
    const val MODERATE_UV = 4.0
    const val HIGH_UV = 7.0
    const val VERY_HIGH_UV = 9.0
    const val EXTREME_UV = 12.0
  }
}
