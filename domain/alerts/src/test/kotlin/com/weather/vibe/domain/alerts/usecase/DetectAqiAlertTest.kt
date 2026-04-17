package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.MEASURED_AT
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class DetectAqiAlertTest {

  private val detect = DetectAqiAlert()

  @Test
  fun `given aqi below poor threshold, when invoked, then no alert returned`() {

    expectThat(detect(airQuality(europeanAqi = AirQualityFixtures.MODERATE_AQI))).isNull()
  }

  @Test
  fun `given aqi in poor range, when invoked, then poor level alert returned`() {

    val alert = detect(airQuality(europeanAqi = AirQualityFixtures.POOR_AQI))

    expectThat(alert).isNotNull().get { level }.isEqualTo(POOR)
  }

  @Test
  fun `given aqi extremely high, when invoked, then extremely poor level alert returned`() {

    val alert = detect(airQuality(europeanAqi = AirQualityFixtures.EXTREMELY_POOR_AQI))

    expectThat(alert).isNotNull().get { level }.isEqualTo(EXTREMELY_POOR)
  }

  @Test
  fun `given poor aqi, when invoked, then alert carries measurement timestamp`() {

    val alert = detect(airQuality(europeanAqi = AirQualityFixtures.POOR_AQI))

    expectThat(alert).isNotNull().get { expectedAt }.isEqualTo(MEASURED_AT)
  }
}
