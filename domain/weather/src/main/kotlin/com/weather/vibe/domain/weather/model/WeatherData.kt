package com.weather.vibe.domain.weather.model

data class WeatherData(
  val cityName: String,
  val latitude: Double,
  val longitude: Double,
  val currentTemperature: Double,
  val condition: WeatherCondition,
  val windSpeed: Double,
  val windDirection: Double,
  val humidity: Int,
  val isDay: Boolean,
  val hourlyForecast: List<HourlyWeather>,
  val dailyForecast: List<DailyWeather>
)
