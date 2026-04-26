package com.weather.vibe.data.viberating.local.mapper

import com.weather.vibe.data.viberating.local.entity.RatingEntryEntity
import com.weather.vibe.domain.viberating.model.RatingEntry
import org.koin.core.annotation.Factory

@Factory
internal class RatingEntryMapper(
  private val weatherSnapshotMapper: WeatherSnapshotMapper
) {

  fun toDomain(entity: RatingEntryEntity): RatingEntry =
    RatingEntry(
      id = entity.id,
      date = entity.date,
      rating = entity.rating,
      weather = weatherSnapshotMapper.toDomain(entity.weather),
      createdAtEpochMs = entity.createdAtEpochMs,
      note = entity.note
    )

  fun toEntity(entry: RatingEntry): RatingEntryEntity =
    RatingEntryEntity(
      id = entry.id,
      date = entry.date,
      rating = entry.rating,
      weather = weatherSnapshotMapper.toEmbedded(entry.weather),
      createdAtEpochMs = entry.createdAtEpochMs,
      note = entry.note
    )
}
