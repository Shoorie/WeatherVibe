package com.weather.vibe.domain.location.policy

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import org.koin.core.annotation.Factory
import java.time.Duration
import java.time.Instant.ofEpochMilli

@Factory
class LocationWeatherFreshnessPolicy(
  private val timeProvider: TimeProvider
) {

  fun needsRefresh(snapshot: LocationWeatherSnapshot?): Boolean {
    if (snapshot == null) return true
    val now = ofEpochMilli(timeProvider.nowEpochMillis())
    return Duration.between(snapshot.updatedAt, now) >= FRESHNESS_WINDOW
  }

  companion object {
    val FRESHNESS_WINDOW: Duration = Duration.ofMinutes(30)
  }
}
