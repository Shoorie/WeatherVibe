package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.testing.time.fixture.FakeTimeProvider
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.SHORT_DAY
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.TODAY
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.TODAY_SUNRISE
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.TODAY_SUNSET
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.dailyWeather
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.time.Duration

class ResolveTodaySunInfoTest {

  private val fakeTimeProvider = FakeTimeProvider()

  private val resolveTodaySunInfo = ResolveTodaySunInfo(
    calculateDayLength = CalculateDayLength(),
    calculateSunProgress = CalculateSunProgress(timeProvider = fakeTimeProvider)
  )

  @Test
  fun `given today sun times, when resolved, then sunrise comes from first day`() {

    val result = resolveTodaySunInfo(days = listOf(TODAY))

    expectThat(result).isNotNull()
      .get { sunrise }.isEqualTo(TODAY_SUNRISE)
  }

  @Test
  fun `given today sun times, when resolved, then sunset comes from first day`() {

    val result = resolveTodaySunInfo(days = listOf(TODAY))

    expectThat(result).isNotNull()
      .get { sunset }.isEqualTo(TODAY_SUNSET)
  }

  @Test
  fun `given today sun times, when resolved, then day length is computed`() {

    val result = resolveTodaySunInfo(days = listOf(TODAY))

    expectThat(result).isNotNull()
      .get { dayLength }.isEqualTo(Duration.ofHours(13).plusMinutes(30))
  }

  @Test
  fun `given short day, when resolved, then day length reflects shorter span`() {

    val result = resolveTodaySunInfo(days = listOf(SHORT_DAY))

    expectThat(result).isNotNull()
      .get { dayLength }.isEqualTo(Duration.ofHours(8).plusMinutes(30))
  }

  @Test
  fun `given multiple days, when resolved, then only first day is used`() {

    val otherDay = dailyWeather(sunrise = null, sunset = null)

    val result = resolveTodaySunInfo(days = listOf(TODAY, otherDay))

    expectThat(result).isNotNull()
      .get { sunrise }.isEqualTo(TODAY_SUNRISE)
  }

  @Test
  fun `given empty forecast, when resolved, then return null`() {

    val result = resolveTodaySunInfo(days = emptyList())

    expectThat(result).isNull()
  }

  @Test
  fun `given null sunrise, when resolved, then return null`() {

    val noSunrise = dailyWeather(sunrise = null, sunset = TODAY_SUNSET)

    val result = resolveTodaySunInfo(days = listOf(noSunrise))

    expectThat(result).isNull()
  }

  @Test
  fun `given null sunset, when resolved, then return null`() {

    val noSunset = dailyWeather(sunrise = TODAY_SUNRISE, sunset = null)

    val result = resolveTodaySunInfo(days = listOf(noSunset))

    expectThat(result).isNull()
  }
}
