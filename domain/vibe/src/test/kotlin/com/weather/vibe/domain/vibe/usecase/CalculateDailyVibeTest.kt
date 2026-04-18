package com.weather.vibe.domain.vibe.usecase

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.model.VibeMood.PLEASANT
import com.weather.vibe.domain.vibe.model.VibeMood.RADIANT
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.GOOD_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.VERY_POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.CALM
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.HIGH_BIRCH
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.hourlyWeather
import com.weather.vibe.testing.weather.fixture.WeatherDataFixtures.weatherData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan

class CalculateDailyVibeTest {

  private val calculate = CalculateDailyVibe(
    scoreAqiBurden = ScoreAqiBurden(),
    scorePollenBurden = ScorePollenBurden(),
    scoreRainOutlook = ScoreRainOutlook(),
    scoreTemperatureComfort = ScoreTemperatureComfort(),
    scoreUvBurden = ScoreUvBurden(),
    scoreWindComfort = ScoreWindComfort()
  )

  private val cleanReadings = EnvironmentalReadings(
    airQuality = airQuality(europeanAqi = GOOD_AQI),
    pollen = CALM
  )

  @Test
  fun `given ideal weather and clean readings, then mood is radiant`() = runTest {

    val vibe = calculate(
      weather = weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND),
      readings = cleanReadings
    ).getOrThrow()

    expectThat(vibe.mood).isEqualTo(RADIANT)
  }

  @Test
  fun `given apparent temperature near freezing, then score drops below comfort`() = runTest {

    val vibe = calculate(
      weather = weatherData(apparentTemperature = FREEZING_TEMPERATURE),
      readings = cleanReadings
    ).getOrThrow()

    expectThat(vibe.score).isLessThan(COMFORT_FLOOR)
  }

  @Test
  fun `given heavy rain hours, then mood drops below pleasant`() = runTest {

    val vibe = calculate(
      weather = weatherData(hourlyForecast = rainyHours()),
      readings = cleanReadings
    ).getOrThrow()

    expectThat(vibe.mood).isLessThan(PLEASANT)
  }

  @Test
  fun `given gale winds, then score drops below calm baseline`() = runTest {

    val calm = calculate(
      weather = weatherData(windSpeed = CALM_WIND),
      readings = cleanReadings
    ).getOrThrow()

    val gale = calculate(
      weather = weatherData(windSpeed = GALE_WIND),
      readings = cleanReadings
    ).getOrThrow()

    expectThat(gale.score).isLessThan(calm.score)
  }

  @Test
  fun `given poor air quality, then score clearly lower than with good air`() = runTest {

    val withGoodAir = calculate(
      weather = weatherData(),
      readings = cleanReadings
    ).getOrThrow()

    val withPoorAir = calculate(
      weather = weatherData(),
      readings = cleanReadings.copy(airQuality = airQuality(europeanAqi = POOR_AQI))
    ).getOrThrow()

    expectThat(withGoodAir.score - withPoorAir.score).isGreaterThan(POOR_AQI_GAP_FLOOR)
  }

  @Test
  fun `given high pollen reading, then score drops by pollen penalty`() = runTest {

    val calmDay = calculate(
      weather = weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND),
      readings = cleanReadings
    ).getOrThrow()

    val pollenDay = calculate(
      weather = weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND),
      readings = cleanReadings.copy(pollen = HIGH_BIRCH)
    ).getOrThrow()

    expectThat(calmDay.score - pollenDay.score).isEqualTo(HIGH_POLLEN_PENALTY)
  }

  @Test
  fun `given no air quality reading, then score uses weather alone`() = runTest {

    val vibe = calculate(
      weather = weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND),
      readings = cleanReadings.copy(airQuality = null)
    ).getOrThrow()

    expectThat(vibe.mood).isEqualTo(RADIANT)
  }

  @Test
  fun `given no pollen reading, then score uses weather alone`() = runTest {

    val vibe = calculate(
      weather = weatherData(apparentTemperature = IDEAL_TEMPERATURE, windSpeed = CALM_WIND),
      readings = cleanReadings.copy(pollen = null)
    ).getOrThrow()

    expectThat(vibe.mood).isEqualTo(RADIANT)
  }

  @Test
  fun `given harsh weather and very poor air, then mood is dreary or worse`() = runTest {

    val vibe = calculate(
      weather = weatherData(
        apparentTemperature = FREEZING_TEMPERATURE,
        hourlyForecast = rainyHours(),
        windSpeed = GALE_WIND
      ),
      readings = cleanReadings.copy(airQuality = airQuality(europeanAqi = VERY_POOR_AQI))
    ).getOrThrow()

    expectThat(vibe.mood).isLessThan(OKAY)
  }

  @Test
  fun `given extreme penalties across the board, then score stays within zero to hundred range`() =
    runTest {

      val vibe = calculate(
        weather = weatherData(
          apparentTemperature = EXTREME_HEAT,
          hourlyForecast = rainyHours(),
          windSpeed = GALE_WIND
        ),
        readings = EnvironmentalReadings(
          airQuality = airQuality(europeanAqi = VERY_POOR_AQI),
          pollen = HIGH_BIRCH
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
