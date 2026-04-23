package com.weather.vibe.domain.location.usecase

import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import org.koin.core.annotation.Factory

@Factory
class RestoreLocationWeatherSnapshot(
  private val snapshotRepository: LocationWeatherSnapshotRepository
) {

  suspend operator fun invoke(snapshot: LocationWeatherSnapshot) {
    snapshotRepository.save(snapshot = snapshot)
  }
}
