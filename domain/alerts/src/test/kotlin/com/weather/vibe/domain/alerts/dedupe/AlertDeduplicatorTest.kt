package com.weather.vibe.domain.alerts.dedupe

import com.weather.vibe.domain.alerts.model.WeatherAlert
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.heavyRain
import com.weather.vibe.testing.alerts.fixture.WeatherAlertFixtures.thunderstorm
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

    val fresh = deduplicator.filterFresh(
      listOf(thunderstorm(expectedAt = AT_THREE), heavyRain(expectedAt = AT_FOUR))
    )

    expectThat(fresh).hasSize(2)
  }

  @Test
  fun `given same alert already notified, when called again, then filtered out`() {

    deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_THREE)))

    val fresh = deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_THREE)))

    expectThat(fresh).isEmpty()
  }

  @Test
  fun `given same type for different expected hour, when called, then returned as fresh`() {

    deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_THREE)))

    val fresh = deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_FIVE)))

    expectThat(fresh).hasSize(1)
    expectThat(fresh.first()).isA<WeatherAlert.ThunderstormImminent>()
      .get { expectedAt }.isEqualTo(AT_FIVE)
  }

  @Test
  fun `given seconds differ inside same hour, when called, then treated as duplicate`() {

    deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_THREE)))

    val fresh = deduplicator.filterFresh(listOf(thunderstorm(expectedAt = AT_THREE.withSecond(42))))

    expectThat(fresh).isEmpty()
  }

  private companion object {
    val AT_THREE: LocalDateTime = LocalDateTime.of(2026, 4, 16, 15, 0)
    val AT_FOUR: LocalDateTime = LocalDateTime.of(2026, 4, 16, 16, 0)
    val AT_FIVE: LocalDateTime = LocalDateTime.of(2026, 4, 16, 17, 0)
  }
}
