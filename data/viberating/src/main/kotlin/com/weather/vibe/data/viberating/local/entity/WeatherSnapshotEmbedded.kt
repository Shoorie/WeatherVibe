package com.weather.vibe.data.viberating.local.entity

import androidx.room.ColumnInfo
import com.weather.vibe.domain.viberating.model.Condition
import com.weather.vibe.domain.viberating.model.PollenLevel

internal data class WeatherSnapshotEmbedded(
  @ColumnInfo(name = "temperature_c") val temperatureC: Double,
  @ColumnInfo(name = "feels_like_c") val feelsLikeC: Double,
  @ColumnInfo(name = "condition") val condition: Condition,
  @ColumnInfo(name = "humidity_percent") val humidityPercent: Int,
  @ColumnInfo(name = "wind_kph") val windKph: Double,
  @ColumnInfo(name = "pressure_hpa") val pressureHpa: Int,
  @ColumnInfo(name = "aqi") val airQualityIndex: Int?,
  @ColumnInfo(name = "pollen_level") val pollenLevel: PollenLevel?
)
