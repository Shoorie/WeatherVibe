package com.weather.vibe.domain.weather.model

data class WeatherData(
  val apparentTemperature: Double,
  val cityName: String,
  val cloudCover: Int = 0,
  val condition: WeatherCondition,
  val currentTemperature: Double,
  val dailyForecast: List<DailyWeather>,
  val dewPoint: Double = 0.0,
  val hourlyForecast: List<HourlyWeather>,
  val humidity: Int,
  val isDay: Boolean,
  val latitude: Double,
  val longitude: Double,
  val precipitation: Double = 0.0,
  val surfacePressure: Double = 0.0,
  val visibility: Double = 0.0,
  val windDirection: Double,
  val windGusts: Double = 0.0,
  val windSpeed: Double
)
