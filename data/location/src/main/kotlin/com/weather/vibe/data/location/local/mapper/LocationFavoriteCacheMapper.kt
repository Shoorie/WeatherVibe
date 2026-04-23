package com.weather.vibe.data.location.local.mapper

import com.weather.vibe.data.location.local.entity.LocationFavoriteEntity
import com.weather.vibe.domain.location.model.LocationFavorite
import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
internal class LocationFavoriteCacheMapper {

  fun toDomain(entity: LocationFavoriteEntity): LocationFavorite =
    LocationFavorite(
      id = entity.id,
      isDefault = entity.isDefault,
      label = entity.label,
      location = Location(
        id = entity.locationId,
        admin1 = entity.admin1,
        country = entity.country,
        latitude = entity.latitude,
        longitude = entity.longitude,
        name = entity.name
      ),
      position = entity.position
    )

  fun toEntity(
    location: Location,
    label: String?,
    position: Int,
    isDefault: Boolean
  ): LocationFavoriteEntity =
    LocationFavoriteEntity(
      admin1 = location.admin1,
      country = location.country,
      isDefault = isDefault,
      label = label,
      latitude = location.latitude,
      locationId = location.id,
      longitude = location.longitude,
      name = location.name,
      position = position
    )
}
