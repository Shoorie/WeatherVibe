package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WindDirection.E
import com.weather.vibe.domain.weather.model.WindDirection.N
import com.weather.vibe.domain.weather.model.WindDirection.NE
import com.weather.vibe.domain.weather.model.WindDirection.NW
import com.weather.vibe.domain.weather.model.WindDirection.S
import com.weather.vibe.domain.weather.model.WindDirection.SE
import com.weather.vibe.domain.weather.model.WindDirection.SW
import com.weather.vibe.domain.weather.model.WindDirection.W
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ComputeWindDirectionTest {

  private val computeWindDirection = ComputeWindDirection()

  @Test
  fun `when degrees is zero, then return north`() {

    val result = computeWindDirection(0.0)

    expectThat(result).isEqualTo(N)
  }

  @Test
  fun `when degrees is 45, then return north east`() {

    val result = computeWindDirection(45.0)

    expectThat(result).isEqualTo(NE)
  }

  @Test
  fun `when degrees is 90, then return east`() {

    val result = computeWindDirection(90.0)

    expectThat(result).isEqualTo(E)
  }

  @Test
  fun `when degrees is 135, then return south east`() {

    val result = computeWindDirection(135.0)

    expectThat(result).isEqualTo(SE)
  }

  @Test
  fun `when degrees is 180, then return south`() {

    val result = computeWindDirection(180.0)

    expectThat(result).isEqualTo(S)
  }

  @Test
  fun `when degrees is 225, then return south west`() {

    val result = computeWindDirection(225.0)

    expectThat(result).isEqualTo(SW)
  }

  @Test
  fun `when degrees is 270, then return west`() {

    val result = computeWindDirection(270.0)

    expectThat(result).isEqualTo(W)
  }

  @Test
  fun `when degrees is 315, then return north west`() {

    val result = computeWindDirection(315.0)

    expectThat(result).isEqualTo(NW)
  }

  @Test
  fun `when degrees is 360, then wrap back to north`() {

    val result = computeWindDirection(360.0)

    expectThat(result).isEqualTo(N)
  }

  @Test
  fun `given degrees above 360, when computed, then normalize to range`() {

    val result = computeWindDirection(720.0)

    expectThat(result).isEqualTo(N)
  }

  @Test
  fun `given negative degrees, when computed, then normalize to range`() {

    val result = computeWindDirection(-45.0)

    expectThat(result).isEqualTo(NW)
  }

  @Test
  fun `when degrees near sector edge, then round to nearest sector`() {

    val result = computeWindDirection(22.4)

    expectThat(result).isEqualTo(N)
  }

  @Test
  fun `when degrees past sector midpoint, then advance to next sector`() {

    val result = computeWindDirection(22.6)

    expectThat(result).isEqualTo(NE)
  }
}
