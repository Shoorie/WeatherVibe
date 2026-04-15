package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.data.widget.persistence.WidgetLocationEntry
import com.weather.vibe.domain.location.model.Location
import org.koin.core.annotation.Factory

@Factory
internal class WidgetLocationEntryMapper {

  fun toDomain(entry: WidgetLocationEntry): Location =
    Location(
      id = entry.id,
      name = entry.name,
      admin1 = entry.admin1.ifBlank { null },
      country = entry.country,
      latitude = entry.latitude,
      longitude = entry.longitude
    )

  fun toEntry(location: Location): WidgetLocationEntry =
    WidgetLocationEntry.newBuilder()
      .setId(location.id)
      .setName(location.name)
      .setAdmin1(location.admin1.orEmpty())
      .setCountry(location.country)
      .setLatitude(location.latitude)
      .setLongitude(location.longitude)
      .build()
}
