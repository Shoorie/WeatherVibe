package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.AqiLevel.EXTREMELY_POOR
import com.weather.vibe.domain.airquality.model.AqiLevel.POOR
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.EXTREMELY_POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.MEASURED_AT
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.MODERATE_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.POOR_AQI
import com.weather.vibe.testing.airquality.fixture.AirQualityFixtures.airQuality
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class DetectAqiAlertTest {

  private val detect = DetectAqiAlert()

  @Test
  fun `when aqi below poor threshold, then no alert returned`() {

    val alert = detect(airQuality(europeanAqi = MODERATE_AQI))

    expectThat(alert).isNull()
  }

  @Test
  fun `when aqi in poor range, then alert carries poor level`() {

    val alert = detect(airQuality(europeanAqi = POOR_AQI))

    expectThat(alert).isNotNull().get { level }.isEqualTo(POOR)
  }

  @Test
  fun `when aqi extremely high, then alert carries extremely poor level`() {

    val alert = detect(airQuality(europeanAqi = EXTREMELY_POOR_AQI))

    expectThat(alert).isNotNull().get { level }.isEqualTo(EXTREMELY_POOR)
  }

  @Test
  fun `when aqi in poor range, then alert carries measurement timestamp`() {

    val alert = detect(airQuality(europeanAqi = POOR_AQI))

    expectThat(alert).isNotNull().get { expectedAt }.isEqualTo(MEASURED_AT)
  }
}
