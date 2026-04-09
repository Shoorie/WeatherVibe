package com.weather.vibe.feature.home.presentation

import com.weather.vibe.feature.home.presentation.fake.FakeTimeProvider
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.SHORT_DAY
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.TODAY
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.TODAY_SUNSET
import com.weather.vibe.feature.home.presentation.fixture.WeatherDataFixtures.dailyWeather
import com.weather.vibe.feature.home.ui.HomeResources
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import java.time.LocalDateTime

class SunriseSunsetStateFactoryTest {

  private val resources: HomeResources = fakeHomeResources()
  private val fakeTimeProvider = FakeTimeProvider()

  private val factory: SunriseSunsetStateFactory =
    SunriseSunsetStateFactory(
      resources = resources,
      timeProvider = fakeTimeProvider
    )

  @Test
  fun `when state created, then format sunrise time`() {

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunriseTime)
      .isEqualTo("06:00")
  }

  @Test
  fun `when state created, then format sunset time`() {

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunsetTime)
      .isEqualTo("19:30")
  }

  @Test
  fun `when state created, then calculate day length`() {

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.dayLength)
      .isEqualTo("13h 30min")
  }

  @Test
  fun `given time at noon, when state created, then sun progress is around midday`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 12, 45)

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunProgress)
      .isGreaterThan(0.4f)
      .isLessThan(0.6f)
  }

  @Test
  fun `given time before sunrise, when state created, then sun progress is zero`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 4, 0)

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunProgress).isEqualTo(0f)
  }

  @Test
  fun `given time after sunset, when state created, then sun progress is one`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 22, 0)

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunProgress).isEqualTo(1f)
  }

  @Test
  fun `given time at sunrise, when state created, then sun progress is zero`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 6, 0)

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunProgress).isEqualTo(0f)
  }

  @Test
  fun `given time at sunset, when state created, then sun progress is one`() {

    fakeTimeProvider.current = LocalDateTime.of(2026, 4, 8, 19, 30)

    val result = factory.create(days = listOf(TODAY))

    expectThat(result.sunProgress).isEqualTo(1f)
  }

  @Test
  fun `given empty forecast, when state created, then sunrise time is empty`() {

    val result = factory.create(days = emptyList())

    expectThat(result.sunriseTime).isEqualTo("")
  }

  @Test
  fun `given empty forecast, when state created, then sunset time is empty`() {

    val result = factory.create(days = emptyList())

    expectThat(result.sunsetTime).isEqualTo("")
  }

  @Test
  fun `given empty forecast, when state created, then sun progress is zero`() {

    val result = factory.create(days = emptyList())

    expectThat(result.sunProgress).isEqualTo(0f)
  }

  @Test
  fun `given empty forecast, when state created, then day length is empty`() {

    val result = factory.create(days = emptyList())

    expectThat(result.dayLength).isEqualTo("")
  }

  @Test
  fun `given invalid sunrise format, when state created, then return empty sunrise`() {

    val invalid = dailyWeather(sunrise = "not-a-date", sunset = TODAY_SUNSET)

    val result = factory.create(days = listOf(invalid))

    expectThat(result.sunriseTime).isEqualTo("not-a-date")
  }

  @Test
  fun `given empty sunrise, when state created, then sun progress is zero`() {

    val noSunrise = dailyWeather(sunrise = "", sunset = TODAY_SUNSET)

    val result = factory.create(days = listOf(noSunrise))

    expectThat(result.sunProgress).isEqualTo(0f)
  }

  @Test
  fun `given short day, when state created, then calculate shorter day length`() {

    val result = factory.create(days = listOf(SHORT_DAY))

    expectThat(result.dayLength).isEqualTo("8h 30min")
  }
}
