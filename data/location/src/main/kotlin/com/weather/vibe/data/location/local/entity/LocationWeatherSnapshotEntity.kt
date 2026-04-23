package com.weather.vibe.data.location.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = "location_weather_snapshot",
  foreignKeys = [
    ForeignKey(
      entity = LocationFavoriteEntity::class,
      parentColumns = ["locationId"],
      childColumns = ["locationId"],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
data class LocationWeatherSnapshotEntity(
  @PrimaryKey val locationId: Long,
  val condition: String,
  val feelsLikeC: Double,
  val highC: Double,
  val hourlyTemperaturesJson: String,
  val humidityPercent: Int,
  val isDay: Boolean,
  val lowC: Double,
  val precipitationChancePercent: Int,
  val temperatureC: Double,
  val updatedAtEpochMs: Long,
  val windKph: Double
)
