package com.weather.vibe.domain.alerts.usecase

import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.LOW
import com.weather.vibe.domain.airquality.model.PollenLevel.MODERATE
import com.weather.vibe.domain.airquality.model.PollenLevel.VERY_HIGH
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.airquality.model.PollenSpecies.OLIVE
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.MEASURED_AT
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollen
import com.weather.vibe.testing.airquality.fixture.PollenFixtures.pollenReading
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class DetectPollenAlertTest {

  private val detect = DetectPollenAlert()

  @Test
  fun `when all readings below high threshold, then no alert returned`() {

    val readings = pollen(
      readings = listOf(
        pollenReading(species = BIRCH, level = LOW),
        pollenReading(species = GRASS, level = MODERATE)
      )
    )

    expectThat(detect(readings)).isNull()
  }

  @Test
  fun `when single species reaches high level, then alert carries that species`() {

    val readings = pollen(
      readings = listOf(
        pollenReading(species = BIRCH, level = HIGH),
        pollenReading(species = GRASS, level = LOW)
      )
    )

    expectThat(detect(readings)).isNotNull().get { species }.containsExactlyInAnyOrder(BIRCH)
  }

  @Test
  fun `when multiple species elevated, then alert carries all of them`() {

    val readings = pollen(
      readings = listOf(
        pollenReading(species = BIRCH, level = HIGH),
        pollenReading(species = GRASS, level = VERY_HIGH),
        pollenReading(species = OLIVE, level = LOW)
      )
    )

    expectThat(detect(readings)).isNotNull().get { species }.containsExactlyInAnyOrder(BIRCH, GRASS)
  }

  @Test
  fun `when species at very high level, then alert still raised`() {

    val readings = pollen(
      readings = listOf(pollenReading(species = GRASS, level = VERY_HIGH))
    )

    expectThat(detect(readings)).isNotNull().get { species }.containsExactlyInAnyOrder(GRASS)
  }

  @Test
  fun `when readings empty, then no alert returned`() {

    expectThat(detect(pollen(readings = emptyList()))).isNull()
  }

  @Test
  fun `when alert raised, then alert carries measurement timestamp`() {

    val readings = pollen(
      readings = listOf(pollenReading(species = BIRCH, level = HIGH))
    )

    expectThat(detect(readings)).isNotNull().get { expectedAt }.isEqualTo(MEASURED_AT)
  }
}
