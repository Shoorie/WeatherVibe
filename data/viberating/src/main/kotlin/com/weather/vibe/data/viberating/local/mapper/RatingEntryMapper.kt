package com.weather.vibe.data.viberating.local.mapper

import com.weather.vibe.data.viberating.local.entity.RatingEntryEntity
import com.weather.vibe.data.viberating.local.entity.WeatherSnapshotEmbedded
import com.weather.vibe.domain.viberating.model.RatingEntry
import com.weather.vibe.domain.viberating.model.WeatherSnapshot

internal fun RatingEntryEntity.toDomain(): RatingEntry =
  RatingEntry(
    date = date,
    rating = rating,
    note = note,
    weather = weather.toDomain(),
    createdAtEpochMs = createdAtEpochMs
  )

internal fun RatingEntry.toEntity(): RatingEntryEntity =
  RatingEntryEntity(
    date = date,
    rating = rating,
    note = note,
    weather = weather.toEmbedded(),
    createdAtEpochMs = createdAtEpochMs
  )

private fun WeatherSnapshotEmbedded.toDomain(): WeatherSnapshot =
  WeatherSnapshot(
    temperatureC = temperatureC,
    feelsLikeC = feelsLikeC,
    condition = condition,
    humidityPercent = humidityPercent,
    windKph = windKph,
    pressureHpa = pressureHpa,
    airQualityIndex = airQualityIndex,
    pollenLevel = pollenLevel
  )

private fun WeatherSnapshot.toEmbedded(): WeatherSnapshotEmbedded =
  WeatherSnapshotEmbedded(
    temperatureC = temperatureC,
    feelsLikeC = feelsLikeC,
    condition = condition,
    humidityPercent = humidityPercent,
    windKph = windKph,
    pressureHpa = pressureHpa,
    airQualityIndex = airQualityIndex,
    pollenLevel = pollenLevel
  )
