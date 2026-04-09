package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.fake.FakeTimeProvider
import com.weather.vibe.domain.weather.model.WindDirection
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.dailyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class GetCurrentWeatherMetricsTest {

  private val fakeTimeProvider = FakeTimeProvider()

  private val getCurrentWeatherMetrics = GetCurrentWeatherMetrics(
    computeWindDirection = ComputeWindDirection(),
    findCurrentHourIndex = FindCurrentHourIndex(timeProvider = fakeTimeProvider)
  )

  @Test
  fun `given weather data, when resolved, then humidity comes from root`() {

    val result = getCurrentWeatherMetrics(weatherData(humidity = 77))

    expectThat(result.humidity).isEqualTo(77)
  }

  @Test
  fun `given weather data, when resolved, then wind speed comes from root`() {

    val result = getCurrentWeatherMetrics(weatherData(windSpeed = 42.0))

    expectThat(result.windSpeed).isEqualTo(42.0)
  }

  @Test
  fun `given zero degrees, when resolved, then wind direction is north`() {

    val result = getCurrentWeatherMetrics(weatherData(windDirection = 0.0))

    expectThat(result.windDirection).isEqualTo(WindDirection.N)
  }

  @Test
  fun `given 180 degrees, when resolved, then wind direction is south`() {

    val result = getCurrentWeatherMetrics(weatherData(windDirection = 180.0))

    expectThat(result.windDirection).isEqualTo(WindDirection.S)
  }

  @Test
  fun `given daily forecast, when resolved, then max wind speed comes from first day`() {

    val weather = weatherData(
      dailyForecast = listOf(
        dailyWeather(windSpeedMax = 55.0),
        dailyWeather(windSpeedMax = 99.0)
      )
    )

    val result = getCurrentWeatherMetrics(weather)

    expectThat(result.windSpeedMax).isEqualTo(55.0)
  }

  @Test
  fun `given daily forecast, when resolved, then uv index comes from first day`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(uvIndexMax = 7.5)))

    val result = getCurrentWeatherMetrics(weather)

    expectThat(result.uvIndexMax).isEqualTo(7.5)
  }

  @Test
  fun `given daily forecast, when resolved, then precipitation sum comes from first day`() {

    val weather = weatherData(dailyForecast = listOf(dailyWeather(precipitationSum = 12.3)))

    val result = getCurrentWeatherMetrics(weather)

    expectThat(result.precipitationSum).isEqualTo(12.3)
  }

  @Test
  fun `given current hour matches forecast, when resolved, then probability comes from current hour`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 13, 30)
    val weather = weatherData(
      hourlyForecast = listOf(
        hourlyWeather(
          time = LocalDateTime.of(2026, 4, 8, 12, 0),
          precipitationProbability = 10
        ),
        hourlyWeather(
          time = LocalDateTime.of(2026, 4, 8, 13, 0),
          precipitationProbability = 80
        ),
        hourlyWeather(
          time = LocalDateTime.of(2026, 4, 8, 14, 0),
          precipitationProbability = 20
        )
      )
    )

    val result = getCurrentWeatherMetrics(weather)

    expectThat(result.precipitationProbability).isEqualTo(80)
  }

  @Test
  fun `given current time outside forecast window, when resolved, then probability falls back to zero`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 20, 0)
    val weather = weatherData(
      hourlyForecast = listOf(
        hourlyWeather(
          time = LocalDateTime.of(2026, 4, 8, 12, 0),
          precipitationProbability = 40
        )
      )
    )

    val result = getCurrentWeatherMetrics(weather)

    expectThat(result.precipitationProbability).isEqualTo(0)
  }

  @Test
  fun `given empty daily forecast, when resolved, then max wind speed falls back to zero`() {

    val result = getCurrentWeatherMetrics(weatherData(dailyForecast = emptyList()))

    expectThat(result.windSpeedMax).isEqualTo(0.0)
  }

  @Test
  fun `given empty daily forecast, when resolved, then uv index falls back to zero`() {

    val result = getCurrentWeatherMetrics(weatherData(dailyForecast = emptyList()))

    expectThat(result.uvIndexMax).isEqualTo(0.0)
  }

  @Test
  fun `given empty daily forecast, when resolved, then precipitation sum falls back to zero`() {

    val result = getCurrentWeatherMetrics(weatherData(dailyForecast = emptyList()))

    expectThat(result.precipitationSum).isEqualTo(0.0)
  }

  @Test
  fun `given empty hourly forecast, when resolved, then probability falls back to zero`() {

    val result = getCurrentWeatherMetrics(weatherData(hourlyForecast = emptyList()))

    expectThat(result.precipitationProbability).isEqualTo(0)
  }
}
