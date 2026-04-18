package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.VERY_HIGH
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.alerts.model.WeatherAlert.HighPollen
import com.weather.vibe.domain.alerts.model.WeatherAlert.PoorAirQuality
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.GOOD_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.VERY_POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollen
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollenReading
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isNull

class ResolveHomeAlertTest {

  private val resolve = ResolveHomeAlert(
    detectAqiAlert = DetectAqiAlert(),
    detectPollenAlert = DetectPollenAlert()
  )

  @Test
  fun `given alerts disabled, then nothing is resolved even for poor air`() {

    val result = resolve(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = VERY_POOR_AQI),
        pollen = null
      ),
      alertsEnabled = false
    )

    expectThat(result).isNull()
  }

  @Test
  fun `given poor air quality, then aqi alert surfaces`() {

    val result = resolve(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = POOR_AQI),
        pollen = null
      ),
      alertsEnabled = true
    )

    expectThat(result).isA<PoorAirQuality>()
  }

  @Test
  fun `given good air and high pollen, then pollen alert surfaces`() {

    val highPollen = pollen(
      readings = listOf(pollenReading(species = BIRCH, level = HIGH))
    )

    val result = resolve(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = GOOD_AQI),
        pollen = highPollen
      ),
      alertsEnabled = true
    )

    expectThat(result).isA<HighPollen>()
  }

  @Test
  fun `given both poor air and high pollen, then aqi takes precedence`() {

    val highPollen = pollen(
      readings = listOf(pollenReading(species = BIRCH, level = VERY_HIGH))
    )

    val result = resolve(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = POOR_AQI),
        pollen = highPollen
      ),
      alertsEnabled = true
    )

    expectThat(result).isA<PoorAirQuality>()
  }

  @Test
  fun `given no readings, then nothing is resolved`() {

    val result = resolve(
      readings = EnvironmentalReadings.Empty,
      alertsEnabled = true
    )

    expectThat(result).isNull()
  }
}
