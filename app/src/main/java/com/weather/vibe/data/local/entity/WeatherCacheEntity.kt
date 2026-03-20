package com.weather.vibe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(
    @PrimaryKey val locationId: String,
    val cityName: String,
    val currentTemperature: Double,
    val currentConditionName: String,
    val windSpeed: Double,
    val windDirection: Double,
    val humidity: Int,
    val isDay: Boolean,
    val hourlyForecastJson: String,
    val dailyForecastJson: String,
    val lastUpdated: Long
)
