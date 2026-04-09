package com.weather.vibe.data.location.local.mapper

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.location.local.entity.RecentLocationEntity
import com.weather.vibe.domain.location.model.LocationResult
import org.koin.core.annotation.Factory

@Factory
internal class LocationCacheMapper(
  private val timeProvider: TimeProvider
) {

  fun toDomain(entity: RecentLocationEntity): LocationResult =
    LocationResult(
      admin1 = entity.admin1,
      country = entity.country,
      id = entity.id,
      latitude = entity.latitude,
      longitude = entity.longitude,
      name = entity.name
    )

  fun toEntity(location: LocationResult): RecentLocationEntity =
    RecentLocationEntity(
      admin1 = location.admin1,
      country = location.country,
      id = location.id,
      latitude = location.latitude,
      longitude = location.longitude,
      name = location.name,
      timestamp = timeProvider.nowEpochMillis()
    )
}
