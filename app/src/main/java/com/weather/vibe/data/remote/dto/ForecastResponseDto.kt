package com.weather.vibe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerialName("current_weather") val currentWeather: CurrentWeatherDto? = null,
    val hourly: HourlyDataDto? = null,
    val daily: DailyDataDto? = null
)
