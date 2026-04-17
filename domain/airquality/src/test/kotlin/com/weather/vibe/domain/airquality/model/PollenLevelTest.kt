package com.weather.vibe.domain.airquality.model

import com.weather.vibe.domain.airquality.model.PollenLevel.HIGH
import com.weather.vibe.domain.airquality.model.PollenLevel.LOW
import com.weather.vibe.domain.airquality.model.PollenLevel.MODERATE
import com.weather.vibe.domain.airquality.model.PollenLevel.VERY_HIGH
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.airquality.model.PollenSpecies.OLIVE
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PollenLevelTest {

  @Test
  fun `when birch reading below ten grains, then level is low`() {

    val level = PollenLevel.from(BIRCH, grainsPerCubicMetre = 5.0)

    expectThat(level).isEqualTo(LOW)
  }

  @Test
  fun `when birch reading between ten and thirty grains, then level is moderate`() {

    val level = PollenLevel.from(BIRCH, grainsPerCubicMetre = 20.0)

    expectThat(level).isEqualTo(MODERATE)
  }

  @Test
  fun `when birch reading between thirty and hundred grains, then level is high`() {

    val level = PollenLevel.from(BIRCH, grainsPerCubicMetre = 50.0)

    expectThat(level).isEqualTo(HIGH)
  }

  @Test
  fun `when birch reading reaches one hundred grains, then level is very high`() {

    val level = PollenLevel.from(BIRCH, grainsPerCubicMetre = 150.0)

    expectThat(level).isEqualTo(VERY_HIGH)
  }

  @Test
  fun `when grass reading at twenty grains, then level is high`() {

    val level = PollenLevel.from(GRASS, grainsPerCubicMetre = 20.0)

    expectThat(level).isEqualTo(HIGH)
  }

  @Test
  fun `when grass reading at fifty grains, then level is very high`() {

    val level = PollenLevel.from(GRASS, grainsPerCubicMetre = 50.0)

    expectThat(level).isEqualTo(VERY_HIGH)
  }

  @Test
  fun `when olive reading at one hundred grains, then level is moderate`() {

    val level = PollenLevel.from(OLIVE, grainsPerCubicMetre = 100.0)

    expectThat(level).isEqualTo(MODERATE)
  }

  @Test
  fun `when olive reading at three hundred grains, then level is high`() {

    val level = PollenLevel.from(OLIVE, grainsPerCubicMetre = 300.0)

    expectThat(level).isEqualTo(HIGH)
  }
}
