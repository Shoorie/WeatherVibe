package com.weather.vibe.data.location.mapper

import com.weather.vibe.data.location.remote.dto.LocationResultDto
import com.weather.vibe.domain.location.model.LocationResult
import org.koin.core.annotation.Factory

@Factory
internal class LocationDtoMapper {

  fun toDomain(dto: LocationResultDto): LocationResult =
    LocationResult(
      admin1 = dto.admin1,
      country = dto.country.orEmpty(),
      id = dto.id,
      latitude = dto.latitude,
      longitude = dto.longitude,
      name = dto.name
    )
}
