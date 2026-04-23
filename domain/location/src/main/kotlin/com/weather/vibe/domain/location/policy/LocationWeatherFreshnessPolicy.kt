package com.weather.vibe.domain.location.policy

import java.time.Duration
import java.time.Instant

object LocationWeatherFreshnessPolicy {

  val FRESHNESS_WINDOW: Duration = Duration.ofMinutes(30)

  fun isStale(updatedAt: Instant, now: Instant): Boolean =
    Duration.between(updatedAt, now) >= FRESHNESS_WINDOW
}
