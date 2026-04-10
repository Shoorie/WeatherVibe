package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.repository.WeatherRepository
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.COORDINATES
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isSuccess
import java.io.IOException

class GetCurrentTemperatureTest {

  private val repository = mockk<WeatherRepository>()
  private val getCurrentTemperature = GetCurrentTemperature(repository)

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when current temperature requested, then value returned`() = runTest {

    coEvery { repository.getCurrentTemperature(COORDINATES) } returns 22.5

    val result = getCurrentTemperature(COORDINATES).first()

    expectThat(result).isSuccess().isEqualTo(22.5)
  }

  @Test
  fun `given weather service fails, when current temperature requested, then error returned`() = runTest {

    val error = IOException("network down")
    coEvery { repository.getCurrentTemperature(COORDINATES) } throws error

    val result = getCurrentTemperature(COORDINATES).first()

    expectThat(result).isFailure().isEqualTo(error)
  }
}
