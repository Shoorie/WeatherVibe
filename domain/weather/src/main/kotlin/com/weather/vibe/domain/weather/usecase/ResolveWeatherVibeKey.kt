package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FOG
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.FREEZING_RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.OVERCAST
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW
import com.weather.vibe.domain.weather.model.WeatherCondition.SNOW_SHOWERS
import com.weather.vibe.domain.weather.model.WeatherCondition.THUNDERSTORM
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.domain.weather.model.WeatherVibeKey
import org.koin.core.annotation.Factory

@Factory
class ResolveWeatherVibeKey {

  operator fun invoke(data: WeatherData): WeatherVibeKey = when {
    !data.isDay -> WeatherVibeKey.NIGHT
    data.condition in STORMY_CONDITIONS -> WeatherVibeKey.STORMY
    data.condition in SNOWY_CONDITIONS -> WeatherVibeKey.SNOWY
    data.condition in RAINY_CONDITIONS -> WeatherVibeKey.RAINY
    data.condition in CLOUDY_CONDITIONS -> WeatherVibeKey.CLOUDY
    else -> WeatherVibeKey.SUNNY
  }

  private companion object {

    val STORMY_CONDITIONS: Set<WeatherCondition> =
      setOf(THUNDERSTORM)

    val SNOWY_CONDITIONS: Set<WeatherCondition> =
      setOf(SNOW, SNOW_SHOWERS)

    val RAINY_CONDITIONS: Set<WeatherCondition> =
      setOf(
        DRIZZLE,
        FREEZING_DRIZZLE,
        RAIN,
        FREEZING_RAIN,
        RAIN_SHOWERS
      )

    val CLOUDY_CONDITIONS: Set<WeatherCondition> =
      setOf(PARTLY_CLOUDY, OVERCAST, FOG)
  }
}
