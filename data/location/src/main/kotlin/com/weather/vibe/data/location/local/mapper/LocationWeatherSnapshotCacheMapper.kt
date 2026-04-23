package com.weather.vibe.data.location.local.mapper

import com.weather.vibe.data.location.local.entity.LocationWeatherSnapshotEntity
import com.weather.vibe.domain.location.model.LocationWeatherSnapshot
import com.weather.vibe.domain.weather.model.SimplifiedCondition
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import java.time.Instant

@Factory
internal class LocationWeatherSnapshotCacheMapper {

  fun toDomain(entity: LocationWeatherSnapshotEntity): LocationWeatherSnapshot =
    LocationWeatherSnapshot(
      condition = SimplifiedCondition.valueOf(entity.condition),
      feelsLikeC = entity.feelsLikeC,
      highC = entity.highC,
      hourlyTemperaturesC = deserializeHourly(json = entity.hourlyTemperaturesJson),
      humidityPercent = entity.humidityPercent,
      isDay = entity.isDay,
      locationId = entity.locationId,
      lowC = entity.lowC,
      precipitationChancePercent = entity.precipitationChancePercent,
      temperatureC = entity.temperatureC,
      updatedAt = Instant.ofEpochMilli(entity.updatedAtEpochMs),
      windKph = entity.windKph
    )

  fun toEntity(snapshot: LocationWeatherSnapshot): LocationWeatherSnapshotEntity =
    LocationWeatherSnapshotEntity(
      condition = snapshot.condition.name,
      feelsLikeC = snapshot.feelsLikeC,
      highC = snapshot.highC,
      hourlyTemperaturesJson = serializeHourly(values = snapshot.hourlyTemperaturesC),
      humidityPercent = snapshot.humidityPercent,
      isDay = snapshot.isDay,
      locationId = snapshot.locationId,
      lowC = snapshot.lowC,
      precipitationChancePercent = snapshot.precipitationChancePercent,
      temperatureC = snapshot.temperatureC,
      updatedAtEpochMs = snapshot.updatedAt.toEpochMilli(),
      windKph = snapshot.windKph
    )

  private fun serializeHourly(values: List<Double>): String =
    Json.encodeToString(ListSerializer(Double.serializer()), values)

  private fun deserializeHourly(json: String): List<Double> =
    if (json.isBlank()) emptyList()
    else Json.decodeFromString(ListSerializer(Double.serializer()), json)
}
