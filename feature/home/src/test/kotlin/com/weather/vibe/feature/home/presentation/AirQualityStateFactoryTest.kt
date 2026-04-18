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
import com.weather.vibe.feature.home.presentation.factory.AirQualityStateFactory
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

class AirQualityStateFactoryTest {

  private val factory = AirQualityStateFactory(
    resources = fakeHomeAirQualityResources()
  )

  @Test
  fun `when air quality given, then chip label matches level`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = MODERATE_AQI),
        pollen = null
      ),
      alert = null
    )

    expectThat(result.airQualityChip)
      .isNotNull()
      .get { label }
      .isEqualTo(aqiLabel(MODERATE))
  }

  @Test
  fun `when air quality given, then chip indicator matches level`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings(
        airQuality = airQuality(europeanAqi = GOOD_AQI),
        pollen = null
      ),
      alert = null
    )

    expectThat(result.airQualityChip)
      .isNotNull()
      .get { indicator }
      .isEqualTo(aqiIndicator(GOOD))
  }

  @Test
  fun `given no readings, then chips are null`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings.Empty,
      alert = null
    )

    expectThat(result.airQualityChip).isNull()
  }

  @Test
  fun `given empty pollen readings, then chip is null`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings(
        airQuality = null,
        pollen = pollen(readings = emptyList())
      ),
      alert = null
    )

    expectThat(result.pollenChip).isNull()
  }

  @Test
  fun `given only low pollen, then chip is null`() {

    val readings = EnvironmentalReadings(
      airQuality = null,
      pollen = pollen(
        readings = listOf(pollenReading(species = BIRCH, level = LOW))
      )
    )

    val result = factory.createPresentation(readings = readings, alert = null)

    expectThat(result.pollenChip).isNull()
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

    val result = factory.createPresentation(readings = readings, alert = null)

    expectThat(result.pollenChip)
      .isNotNull()
      .get { label }
      .isEqualTo(pollenLabel(VERY_HIGH))
  }

  @Test
  fun `given aqi alert, then banner title comes from aqi resources`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings.Empty,
      alert = poorAirQuality()
    )

    expectThat(result.alert)
      .isNotNull()
      .get { title }
      .isEqualTo(AQI_ALERT_TITLE)
  }

  @Test
  fun `given pollen alert, then banner title comes from pollen resources`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings.Empty,
      alert = highPollen()
    )

    expectThat(result.alert)
      .isNotNull()
      .get { title }
      .isEqualTo(POLLEN_ALERT_TITLE)
  }

  @Test
  fun `given unrelated alert, then banner is null`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings.Empty,
      alert = thunderstorm()
    )

    expectThat(result.alert).isNull()
  }

  @Test
  fun `when no alert passed, then banner is null`() {

    val result = factory.createPresentation(
      readings = EnvironmentalReadings(airQuality = airQuality(), pollen = null),
      alert = null
    )

    expectThat(result.alert).isNull()
  }
}
