package com.weather.vibe.data.weather.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(
  @PrimaryKey val locationId: String,
  val cityName: String,
  val currentConditionName: String,
  val currentTemperature: Double,
  val dailyForecastJson: String,
  val hourlyForecastJson: String,
  val humidity: Int,
  val isDay: Boolean,
  val lastUpdated: Long,
  val windDirection: Double,
  val windSpeed: Double
)
