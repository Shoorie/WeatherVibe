package com.weather.vibe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyWeather(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: WeatherCondition,
    val precipitationProbability: Int
)
