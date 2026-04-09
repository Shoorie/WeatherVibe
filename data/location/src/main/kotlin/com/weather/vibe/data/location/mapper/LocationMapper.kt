package com.weather.vibe.data.location.mapper

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.data.location.local.entity.RecentLocationEntity
import com.weather.vibe.data.location.remote.dto.LocationResultDto
import com.weather.vibe.domain.location.model.LocationResult

fun LocationResultDto.toLocationResult(): LocationResult =
  LocationResult(
    admin1 = admin1,
    country = country.orEmpty(),
    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name
  )

fun RecentLocationEntity.toLocationResult(): LocationResult =
  LocationResult(
    admin1 = admin1,
    country = country,
    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name
  )

fun LocationResult.toRecentEntity(timeProvider: TimeProvider): RecentLocationEntity =
  RecentLocationEntity(
    admin1 = admin1,
    country = country,
    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    timestamp = timeProvider.nowEpochMillis()
  )
