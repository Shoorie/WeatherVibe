package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.airquality.model.AqiLevel.GOOD
import com.weather.vibe.domain.airquality.model.AqiLevel.MODERATE
import com.weather.vibe.domain.airquality.model.EnvironmentalReadings
import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.LOW
import com.weather.vibe.domain.airquality.model.PollenLevel.VERY_HIGH
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.airquality.model.PollenSpecies.MUGWORT
import com.weather.vibe.feature.home.presentation.factory.EnvironmentSectionFactory
import com.weather.vibe.feature.home.presentation.fake.fakeHomeAirQualityResources
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.AQI_ALERT_TITLE
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.POLLEN_ALERT_TITLE
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiIndicator
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.aqiLabel
import com.weather.vibe.feature.home.presentation.fixture.HomeAirQualityResourcesFixtures.pollenLabel
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.GOOD_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.MODERATE_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollen
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollenReading
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.highPollen
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.poorAirQuality
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.thunderstorm
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class EnvironmentSectionFactoryTest {

  private val factory = EnvironmentSectionFactory(
    resources = fakeHomeAirQualityResources()
  )

  @Test
  fun `when air quality given, then chip label matches level`() {

    val result = factory.buildAirQualityChip(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = MODERATE_AQI),
        pollen = null
      )
    )

    expectThat(result)
      .isNotNull()
      .get { label }
      .isEqualTo(aqiLabel(MODERATE))
  }

  @Test
  fun `when air quality given, then chip indicator matches level`() {

    val result = factory.buildAirQualityChip(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = GOOD_AQI),
        pollen = null
      )
    )

    expectThat(result)
      .isNotNull()
      .get { indicator }
      .isEqualTo(aqiIndicator(GOOD))
  }

  @Test
  fun `given no readings, then air quality chip is null`() {

    val result = factory.buildAirQualityChip(readings = EnvironmentalReadings.Empty)

    expectThat(result).isNull()
  }

  @Test
  fun `given empty pollen readings, then pollen chip is null`() {

    val result = factory.buildPollenChip(
      readings = EnvironmentalReadings(
        airQuality = null,
        pollen = pollen(readings = emptyList())
      )
    )

    expectThat(result).isNull()
  }

  @Test
  fun `given only low pollen, then pollen chip is null`() {

    val readings = EnvironmentalReadings(
      airQuality = null,
      pollen = pollen(
        readings = listOf(pollenReading(species = BIRCH, level = LOW))
      )
    )

    val result = factory.buildPollenChip(readings = readings)

    expectThat(result).isNull()
  }

  @Test
  fun `given mixed severity pollen readings, then chip label uses worst notable level`() {

    val readings = EnvironmentalReadings(
      airQuality = null,
      pollen = pollen(
        readings = listOf(
          pollenReading(species = GRASS, level = LOW),
          pollenReading(species = BIRCH, level = VERY_HIGH),
          pollenReading(species = MUGWORT, level = HIGH)
        )
      )
    )

    val result = factory.buildPollenChip(readings = readings)

    expectThat(result)
      .isNotNull()
      .get { label }
      .isEqualTo(pollenLabel(VERY_HIGH))
  }

  @Test
  fun `given aqi alert, then banner title comes from aqi resources`() {

    val result = factory.buildAlert(alert = poorAirQuality())

    expectThat(result)
      .isNotNull()
      .get { title }
      .isEqualTo(AQI_ALERT_TITLE)
  }

  @Test
  fun `given pollen alert, then banner title comes from pollen resources`() {

    val result = factory.buildAlert(alert = highPollen())

    expectThat(result)
      .isNotNull()
      .get { title }
      .isEqualTo(POLLEN_ALERT_TITLE)
  }

  @Test
  fun `given unrelated alert, then banner is null`() {

    val result = factory.buildAlert(alert = thunderstorm())

    expectThat(result).isNull()
  }

  @Test
  fun `when no alert passed, then banner is null`() {

    val result = factory.buildAlert(alert = null)

    expectThat(result).isNull()
  }
}
