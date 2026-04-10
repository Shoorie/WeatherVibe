package com.weather.vibe.data.location.local.mapper

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.location.local.entity.RecentLocationEntity
import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
internal class LocationCacheMapper(
  private val timeProvider: TimeProvider
) {

  fun toDomain(entity: RecentLocationEntity): Location =
    Location(
      id = entity.id,
      name = entity.name,
      admin1 = entity.admin1,
      country = entity.country,
      latitude = entity.latitude,
      longitude = entity.longitude
    )

  fun toEntity(location: Location): RecentLocationEntity =
    RecentLocationEntity(
      id = location.id,
      admin1 = location.admin1,
      country = location.country,
      latitude = location.latitude,
      longitude = location.longitude,
      name = location.name,
      timestamp = timeProvider.nowEpochMillis()
    )
}
