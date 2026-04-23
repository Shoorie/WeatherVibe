package com.weather.vibe.domain.location.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.location.mapper.WeatherDataToSnapshotMapper
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.domain.location.model.toCoordinates
import com.weather.vibe.domain.location.repository.LocationWeatherSnapshotRepository
import com.weather.vibe.domain.weather.usecase.GetWeather
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import java.time.Instant.ofEpochMilli

@Factory
class FetchAndStoreLocationWeather(
  private val getWeather: GetWeather,
  private val snapshotMapper: WeatherDataToSnapshotMapper,
  private val snapshotRepository: LocationWeatherSnapshotRepository,
  private val timeProvider: TimeProvider
) {

  suspend operator fun invoke(location: Location) {
    val weather = getWeather(location.toCoordinates()).first().getOrNull() ?: return
    val snapshot = snapshotMapper.toSnapshot(
      locationId = location.id,
      data = weather,
      capturedAt = ofEpochMilli(timeProvider.nowEpochMillis())
    )
    snapshotRepository.save(snapshot = snapshot)
  }
}
