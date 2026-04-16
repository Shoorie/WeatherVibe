package com.weather.vibe.domain.alerts.dedupe

import com.weather.vibe.domain.alerts.model.WeatherAlert
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import java.time.LocalDateTime

class AlertDeduplicatorTest {

  private val deduplicator = AlertDeduplicator()

  @Test
  fun `when first call with alerts, then all returned`() {

    val fresh = deduplicator.filterFresh(listOf(STORM_AT_THREE, RAIN_AT_FOUR))

    expectThat(fresh).hasSize(2)
  }

  @Test
  fun `given same alert already notified, when called again, then filtered out`() {

    deduplicator.filterFresh(listOf(STORM_AT_THREE))

    val fresh = deduplicator.filterFresh(listOf(STORM_AT_THREE))

    expectThat(fresh).isEmpty()
  }

  @Test
  fun `given same type for different expected hour, when called, then returned as fresh`() {

    deduplicator.filterFresh(listOf(STORM_AT_THREE))

    val fresh = deduplicator.filterFresh(listOf(STORM_AT_FIVE))

    expectThat(fresh).hasSize(1)
    expectThat(fresh.first()).isA<WeatherAlert.ThunderstormImminent>()
      .get { expectedAt }.isEqualTo(AT_FIVE)
  }

  @Test
  fun `given seconds differ inside same hour, when called, then treated as duplicate`() {

    val atThree = WeatherAlert.ThunderstormImminent(expectedAt = AT_THREE)
    val atThreeWithSeconds = WeatherAlert.ThunderstormImminent(expectedAt = AT_THREE.withSecond(42))

    deduplicator.filterFresh(listOf(atThree))

    val fresh = deduplicator.filterFresh(listOf(atThreeWithSeconds))

    expectThat(fresh).isEmpty()
  }

  private companion object {
    val AT_THREE: LocalDateTime = LocalDateTime.of(2026, 4, 16, 15, 0)
    val AT_FOUR: LocalDateTime = LocalDateTime.of(2026, 4, 16, 16, 0)
    val AT_FIVE: LocalDateTime = LocalDateTime.of(2026, 4, 16, 17, 0)
    val STORM_AT_THREE = WeatherAlert.ThunderstormImminent(expectedAt = AT_THREE)
    val STORM_AT_FIVE = WeatherAlert.ThunderstormImminent(expectedAt = AT_FIVE)
    val RAIN_AT_FOUR = WeatherAlert.HeavyRainImminent(expectedAt = AT_FOUR, millimetres = 7.0)
  }
}
