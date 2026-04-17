package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.airquality.usecase.GetAirQuality
import com.weather.vibe.domain.airquality.usecase.GetPollen
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.model.VibeMood.PLEASANT
import com.weather.vibe.domain.vibe.model.VibeMood.RADIANT
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.GOOD_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.VERY_POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.CALM
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.HIGH_BIRCH
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.COORDINATES
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CalculateDailyVibeTest {

  private val getAirQuality = mockk<GetAirQuality>()
  private val getPollen = mockk<GetPollen>()
  private val calculate = CalculateDailyVibe(
    getAirQuality = getAirQuality,
    getPollen = getPollen,
    scoreAqiBurden = ScoreAqiBurden(),
    scorePollenBurden = ScorePollenBurden(),
    scoreRainOutlook = ScoreRainOutlook(),
    scoreTemperatureComfort = ScoreTemperatureComfort(),
    scoreUvBurden = ScoreUvBurden(),
    scoreWindComfort = ScoreWindComfort()
  )

  @Before
  fun setUp() {
    coEvery { getAirQuality(COORDINATES) } returns success(airQuality(europeanAqi = GOOD_AQI))
    coEvery { getPollen(COORDINATES) } returns success(CALM)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `when ideal weather and clean environment, then mood is radiant`() = runTest {

    val vibe = calculate(
      weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND)
    ).getOrThrow()

    expectThat(vibe.mood).isEqualTo(RADIANT)
  }

  @Test
  fun `when apparent temperature near freezing, then score drops below comfort`() = runTest {

    val vibe = calculate(weatherData(apparentTemperature = FREEZING_TEMPERATURE)).getOrThrow()

    expectThat(vibe.score).isLessThan(COMFORT_FLOOR)
  }

  @Test
  fun `when next hours bring heavy rain, then mood drops below pleasant`() = runTest {

    val vibe = calculate(weatherData(hourlyForecast = rainyHours())).getOrThrow()

    expectThat(vibe.mood).isLessThan(PLEASANT)
  }

  @Test
  fun `when wind speed reaches gale, then score drops below calm baseline`() = runTest {

    val calm = calculate(weatherData(windSpeed = CALM_WIND)).getOrThrow()
    val gale = calculate(weatherData(windSpeed = GALE_WIND)).getOrThrow()

    expectThat(gale.score).isLessThan(calm.score)
  }

  @Test
  fun `when air quality poor, then score clearly lower than with good air`() = runTest {

    val withGoodAir = calculate(weatherData()).getOrThrow()

    coEvery { getAirQuality(COORDINATES) } returns success(airQuality(europeanAqi = POOR_AQI))
    val withPoorAir = calculate(weatherData()).getOrThrow()

    expectThat(withGoodAir.score - withPoorAir.score).isGreaterThan(POOR_AQI_GAP_FLOOR)
  }

  @Test
  fun `when pollen reading reaches high, then score drops by pollen penalty`() = runTest {

    val calmDay = calculate(
      weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND)
    ).getOrThrow()

    coEvery { getPollen(COORDINATES) } returns success(HIGH_BIRCH)
    val pollenDay = calculate(
      weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND)
    ).getOrThrow()

    expectThat(calmDay.score - pollenDay.score).isEqualTo(HIGH_POLLEN_PENALTY)
  }

  @Test
  fun `given air quality fetch fails, when vibe calculated, then score uses weather alone`() =
    runTest {

      coEvery { getAirQuality(COORDINATES) } returns failure(IllegalStateException("offline"))

      val vibe = calculate(
        weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND)
      ).getOrThrow()

      expectThat(vibe.mood).isEqualTo(RADIANT)
    }

  @Test
  fun `given pollen fetch fails, when vibe calculated, then score uses weather alone`() = runTest {

    coEvery { getPollen(COORDINATES) } returns failure(IllegalStateException("offline"))

    val vibe = calculate(
      weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND)
    ).getOrThrow()

    expectThat(vibe.mood).isEqualTo(RADIANT)
  }

  @Test
  fun `when weather harsh and air very poor, then mood is dreary or worse`() = runTest {

    coEvery { getAirQuality(COORDINATES) } returns success(airQuality(europeanAqi = VERY_POOR_AQI))

    val vibe = calculate(
      weatherData(
        apparentTemperature = FREEZING_TEMPERATURE,
        hourlyForecast = rainyHours(),
        windSpeed = GALE_WIND
      )
    ).getOrThrow()

    expectThat(vibe.mood).isLessThan(OKAY)
  }

  @Test
  fun `when all penalties apply at extremes, then score stays within zero to hundred range`() =
    runTest {

      coEvery { getAirQuality(COORDINATES) } returns success(airQuality(europeanAqi = VERY_POOR_AQI))
      coEvery { getPollen(COORDINATES) } returns success(HIGH_BIRCH)

      val vibe = calculate(
        weatherData(
          apparentTemperature = EXTREME_HEAT,
          hourlyForecast = rainyHours(),
          windSpeed = GALE_WIND
        )
      ).getOrThrow()

      expectThat(vibe.score).isGreaterThan(SCORE_LOWER_BOUND).and { isLessThan(SCORE_UPPER_BOUND) }
    }

  private fun rainyHours() = List(size = RAIN_SAMPLE_HOURS) {
    hourlyWeather(precipitationProbability = HIGH_RAIN_PROBABILITY)
  }

  private companion object {
    const val IDEAL_TEMPERATURE = 20.0
    const val FREEZING_TEMPERATURE = 0.0
    const val EXTREME_HEAT = 38.0
    const val CALM_WIND = 5.0
    const val GALE_WIND = 65.0
    const val HIGH_RAIN_PROBABILITY = 90
    const val RAIN_SAMPLE_HOURS = 6
    const val COMFORT_FLOOR = 75
    const val POOR_AQI_GAP_FLOOR = 10
    const val HIGH_POLLEN_PENALTY = 10
    const val SCORE_LOWER_BOUND = -1
    const val SCORE_UPPER_BOUND = 101
  }
}
