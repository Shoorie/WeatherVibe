package com.weather.vibe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeather(
    val time: String,
    val temperature: Double,
    val condition: WeatherCondition,
    val humidity: Int,
    val windSpeed: Double,
    val precipitationProbability: Int
)
