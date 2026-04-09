package com.weather.vibe.testing.weather.fixture

import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

object WeatherDataFixtures {

  const val CITY_NAME = "Warsaw"
  const val CURRENT_TEMPERATURE = 22.0
  const val APPARENT_TEMPERATURE = 20.0
  const val HUMIDITY = 65
  const val WIND_SPEED = 15.0
  const val WIND_DIRECTION = 180.0
  const val WIND_GUSTS = 25.0
  const val LATITUDE = 52.23
  const val LONGITUDE = 21.01

  val TODAY_DATE: LocalDate = LocalDate.of(2026, 4, 8)
  val TODAY_SUNRISE: LocalDateTime = LocalDateTime.of(2026, 4, 8, 6, 0)
  val TODAY_SUNSET: LocalDateTime = LocalDateTime.of(2026, 4, 8, 19, 30)
  const val TODAY_MAX_TEMP = 25.0
  const val TODAY_MIN_TEMP = 12.0

  val HOUR_1_TIME: LocalDateTime = LocalDateTime.of(2026, 4, 8, 12, 0)
  val HOUR_2_TIME: LocalDateTime = LocalDateTime.of(2026, 4, 8, 13, 0)
  val HOUR_3_TIME: LocalDateTime = LocalDateTime.of(2026, 4, 8, 14, 0)

  private const val HOUR_1_TEMP = 22.0
  private const val HOUR_2_TEMP = 24.0
  private const val HOUR_3_TEMP = 23.0

  private val TOMORROW_DATE: LocalDate = LocalDate.of(2026, 4, 9)
  private val TOMORROW_SUNRISE: LocalDateTime = LocalDateTime.of(2026, 4, 9, 5, 58)
  private val TOMORROW_SUNSET: LocalDateTime = LocalDateTime.of(2026, 4, 9, 19, 32)
  private const val TOMORROW_MAX_TEMP = 20.0
  private const val TOMORROW_MIN_TEMP = 10.0

  private val RAINY_DAY_DATE: LocalDate = LocalDate.of(2026, 4, 10)
  private val RAINY_DAY_SUNRISE: LocalDateTime = LocalDateTime.of(2026, 4, 10, 5, 56)
  private val RAINY_DAY_SUNSET: LocalDateTime = LocalDateTime.of(2026, 4, 10, 19, 34)
  private const val RAINY_DAY_MAX_TEMP = 14.0
  private const val RAINY_DAY_MIN_TEMP = 8.0

  private val SHORT_DAY_SUNRISE: LocalDateTime = LocalDateTime.of(2026, 4, 8, 7, 30)
  private val SHORT_DAY_SUNSET: LocalDateTime = LocalDateTime.of(2026, 4, 8, 16, 0)

  val TODAY = dailyWeather(
    condition = CLEAR_SKY,
    date = TODAY_DATE,
    maxTemperature = TODAY_MAX_TEMP,
    minTemperature = TODAY_MIN_TEMP,
    sunrise = TODAY_SUNRISE,
    sunset = TODAY_SUNSET
  )

  val TOMORROW = dailyWeather(
    condition = PARTLY_CLOUDY,
    date = TOMORROW_DATE,
    maxTemperature = TOMORROW_MAX_TEMP,
    minTemperature = TOMORROW_MIN_TEMP,
    sunrise = TOMORROW_SUNRISE,
    sunset = TOMORROW_SUNSET
  )

  val RAINY_DAY = dailyWeather(
    condition = RAIN,
    date = RAINY_DAY_DATE,
    maxTemperature = RAINY_DAY_MAX_TEMP,
    minTemperature = RAINY_DAY_MIN_TEMP,
    sunrise = RAINY_DAY_SUNRISE,
    sunset = RAINY_DAY_SUNSET
  )

  val HOUR_1 = hourlyWeather(
    condition = CLEAR_SKY,
    temperature = HOUR_1_TEMP,
    time = HOUR_1_TIME
  )

  val HOUR_2 = hourlyWeather(
    condition = CLEAR_SKY,
    temperature = HOUR_2_TEMP,
    time = HOUR_2_TIME
  )

  val HOUR_3 = hourlyWeather(
    condition = PARTLY_CLOUDY,
    temperature = HOUR_3_TEMP,
    time = HOUR_3_TIME
  )

  val SHORT_DAY = dailyWeather(
    sunrise = SHORT_DAY_SUNRISE,
    sunset = SHORT_DAY_SUNSET
  )

  val WEATHER = weatherData()

  fun weatherData(
    apparentTemperature: Double = APPARENT_TEMPERATURE,
    cityName: String = CITY_NAME,
    cloudCover: Int = 20,
    condition: WeatherCondition = CLEAR_SKY,
    currentTemperature: Double = CURRENT_TEMPERATURE,
    dailyForecast: List<DailyWeather> = listOf(TODAY, TOMORROW, RAINY_DAY),
    dewPoint: Double = 12.0,
    hourlyForecast: List<HourlyWeather> = listOf(HOUR_1, HOUR_2, HOUR_3),
    humidity: Int = HUMIDITY,
    isDay: Boolean = true,
    latitude: Double = LATITUDE,
    longitude: Double = LONGITUDE,
    precipitation: Double = 0.0,
    surfacePressure: Double = 1013.0,
    visibility: Double = 10000.0,
    windDirection: Double = WIND_DIRECTION,
    windGusts: Double = WIND_GUSTS,
    windSpeed: Double = WIND_SPEED
  ): WeatherData = WeatherData(
    apparentTemperature = apparentTemperature,
    cityName = cityName,
    cloudCover = cloudCover,
    condition = condition,
    currentTemperature = currentTemperature,
    dailyForecast = dailyForecast,
    dewPoint = dewPoint,
    hourlyForecast = hourlyForecast,
    humidity = humidity,
    isDay = isDay,
    latitude = latitude,
    longitude = longitude,
    precipitation = precipitation,
    surfacePressure = surfacePressure,
    visibility = visibility,
    windDirection = windDirection,
    windGusts = windGusts,
    windSpeed = windSpeed
  )

  fun dailyWeather(
    condition: WeatherCondition = CLEAR_SKY,
    date: LocalDate = TODAY_DATE,
    maxTemperature: Double = TODAY_MAX_TEMP,
    minTemperature: Double = TODAY_MIN_TEMP,
    precipitationProbability: Int = 10,
    precipitationSum: Double = 0.0,
    sunrise: LocalDateTime? = TODAY_SUNRISE,
    sunset: LocalDateTime? = TODAY_SUNSET,
    uvIndexMax: Double = 5.0,
    windGustsMax: Double = 30.0,
    windSpeedMax: Double = 20.0
  ): DailyWeather = DailyWeather(
    condition = condition,
    date = date,
    maxTemperature = maxTemperature,
    minTemperature = minTemperature,
    precipitationProbability = precipitationProbability,
    precipitationSum = precipitationSum,
    sunrise = sunrise,
    sunset = sunset,
    uvIndexMax = uvIndexMax,
    windGustsMax = windGustsMax,
    windSpeedMax = windSpeedMax
  )

  fun hourlyWeather(
    condition: WeatherCondition = CLEAR_SKY,
    humidity: Int = HUMIDITY,
    precipitationProbability: Int = 10,
    temperature: Double = CURRENT_TEMPERATURE,
    time: LocalDateTime = HOUR_1_TIME,
    windSpeed: Double = WIND_SPEED
  ): HourlyWeather = HourlyWeather(
    condition = condition,
    humidity = humidity,
    precipitationProbability = precipitationProbability,
    temperature = temperature,
    time = time,
    windSpeed = windSpeed
  )
}
