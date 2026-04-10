package com.weather.vibe.data.location.remote.mapper

import com.weather.vibe.data.location.remote.dto.LocationDto
import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
internal class LocationDtoMapper {

  fun toDomain(dto: LocationDto): Location =
    Location(
      id = dto.id,
      name = dto.name,
      admin1 = dto.admin1,
      country = dto.country.orEmpty(),
      latitude = dto.latitude,
      longitude = dto.longitude
    )
}
