package com.weather.vibe.testing.viberating.fixture

import com.weather.vibe.domain.weather.model.Condition
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.viberating.model.WeatherSnapshot

object WeatherSnapshotFixtures {

  val SUNNY_20C: WeatherSnapshot = weatherSnapshot(
    temperatureC = 20.0,
    feelsLikeC = 19.0,
    condition = Condition.SUNNY,
    humidityPercent = 45,
    windKph = 8.0,
    airQualityIndex = 30,
    pollenLevel = PollenLevel.LOW
  )

  val PARTLY_CLOUDY_18C: WeatherSnapshot = weatherSnapshot(
    temperatureC = 18.0,
    feelsLikeC = 17.0,
    condition = Condition.PARTLY_CLOUDY,
    humidityPercent = 55,
    windKph = 12.0
  )

  val RAIN_12C: WeatherSnapshot = weatherSnapshot(
    temperatureC = 12.0,
    feelsLikeC = 9.0,
    condition = Condition.RAIN,
    humidityPercent = 85,
    windKph = 22.0,
    airQualityIndex = 45
  )

  val CLOUDY_14C: WeatherSnapshot = weatherSnapshot(
    temperatureC = 14.0,
    feelsLikeC = 12.0,
    condition = Condition.CLOUDY,
    humidityPercent = 70,
    windKph = 15.0
  )

  val SNOW_MINUS2C: WeatherSnapshot = weatherSnapshot(
    temperatureC = -2.0,
    feelsLikeC = -7.0,
    condition = Condition.SNOW,
    humidityPercent = 80,
    windKph = 20.0
  )

  val NIGHT_8C: WeatherSnapshot = weatherSnapshot(
    temperatureC = 8.0,
    feelsLikeC = 6.0,
    condition = Condition.NIGHT,
    humidityPercent = 65,
    windKph = 10.0
  )

  fun weatherSnapshot(
    temperatureC: Double = 18.0,
    feelsLikeC: Double = 17.0,
    condition: Condition = Condition.PARTLY_CLOUDY,
    humidityPercent: Int = 55,
    windKph: Double = 10.0,
    pressureHpa: Int = 1013,
    airQualityIndex: Int? = null,
    pollenLevel: PollenLevel? = null
  ): WeatherSnapshot = WeatherSnapshot(
    temperatureC = temperatureC,
    feelsLikeC = feelsLikeC,
    condition = condition,
    humidityPercent = humidityPercent,
    windKph = windKph,
    pressureHpa = pressureHpa,
    airQualityIndex = airQualityIndex,
    pollenLevel = pollenLevel
  )
}
