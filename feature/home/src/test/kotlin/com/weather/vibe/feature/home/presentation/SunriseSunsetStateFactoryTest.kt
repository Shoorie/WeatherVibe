package com.weather.vibe.feature.home.presentation

import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.SUN_INFO
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.todaySunInfo
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.Duration

class SunriseSunsetStateFactoryTest {

  private val resources: HomeResources = fakeHomeResources()

  private val factory: SunriseSunsetStateFactory =
    SunriseSunsetStateFactory(resources = resources)

  @Test
  fun `given sun info, when state created, then format sunrise time`() {

    val result = factory.create(SUN_INFO)

    expectThat(result.sunriseTime).isEqualTo("06:00")
  }

  @Test
  fun `given sun info, when state created, then format sunset time`() {

    val result = factory.create(SUN_INFO)

    expectThat(result.sunsetTime).isEqualTo("19:30")
  }

  @Test
  fun `given sun info, when state created, then format day length`() {

    val result = factory.create(todaySunInfo(dayLength = Duration.ofHours(13).plusMinutes(30)))

    expectThat(result.dayLength).isEqualTo("13h 30min")
  }

  @Test
  fun `given short day length, when state created, then format shorter day length`() {

    val result = factory.create(todaySunInfo(dayLength = Duration.ofHours(8).plusMinutes(30)))

    expectThat(result.dayLength).isEqualTo("8h 30min")
  }

  @Test
  fun `given sun info, when state created, then pass through sun progress`() {

    val result = factory.create(todaySunInfo(sunProgress = 0.75f))

    expectThat(result.sunProgress).isEqualTo(0.75f)
  }

  @Test
  fun `given null info, when state created, then sunrise time is empty`() {

    val result = factory.create(info = null)

    expectThat(result.sunriseTime).isEqualTo("")
  }

  @Test
  fun `given null info, when state created, then sunset time is empty`() {

    val result = factory.create(info = null)

    expectThat(result.sunsetTime).isEqualTo("")
  }

  @Test
  fun `given null info, when state created, then day length is empty`() {

    val result = factory.create(info = null)

    expectThat(result.dayLength).isEqualTo("")
  }

  @Test
  fun `given null info, when state created, then sun progress is zero`() {

    val result = factory.create(info = null)

    expectThat(result.sunProgress).isEqualTo(0f)
  }
}
