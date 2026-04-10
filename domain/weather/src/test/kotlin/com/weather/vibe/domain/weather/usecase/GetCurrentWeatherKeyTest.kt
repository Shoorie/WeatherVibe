package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.fake.FakeTimeProvider
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import com.weather.vibe.domain.weather.model.TemperatureRange
import com.weather.vibe.domain.weather.model.TimeOfDay
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.WEATHER
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class GetCurrentWeatherKeyTest {

  private val timeProvider = FakeTimeProvider()

  private val getCurrentWeatherKey = GetCurrentWeatherKey(
    computeWeatherKey = ComputeWeatherKey(),
    timeProvider = timeProvider
  )

  @Test
  fun `given afternoon hour, when key computed, then time of day is afternoon`() {

    timeProvider.current = LocalDateTime.of(2026, 4, 8, 15, 30)

    val result = getCurrentWeatherKey(WEATHER)

    expectThat(result.timeOfDay).isEqualTo(TimeOfDay.AFTERNOON)
  }

  @Test
  fun `given morning hour, when key computed, then time of day is morning`() {

    timeProvider.current = LocalDateTime.of(2026, 4, 8, 8, 0)

    val result = getCurrentWeatherKey(WEATHER)

    expectThat(result.timeOfDay).isEqualTo(TimeOfDay.MORNING)
  }

  @Test
  fun `given rain condition, when key computed, then simplified condition is rainy`() {

    val result = getCurrentWeatherKey(weatherData(condition = RAIN))

    expectThat(result.condition).isEqualTo(SimplifiedCondition.RAINY)
  }

  @Test
  fun `given warm temperature, when key computed, then temperature range is warm`() {

    val result = getCurrentWeatherKey(weatherData(currentTemperature = 25.0))

    expectThat(result.temperature).isEqualTo(TemperatureRange.WARM)
  }

  @Test
  fun `given cold temperature, when key computed, then temperature range is cold`() {

    val result = getCurrentWeatherKey(weatherData(currentTemperature = 2.0))

    expectThat(result.temperature).isEqualTo(TemperatureRange.COLD)
  }
}
