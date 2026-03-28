package com.weather.vibe.domain.weather.cache

import com.weather.vibe.domain.weather.model.WeatherAiContent
import java.time.LocalDate

interface WeatherAiCache {

  suspend fun get(
    cityName: String,
    date: LocalDate
  ): WeatherAiContent?

  suspend fun save(
    cityName: String,
    content: WeatherAiContent,
    date: LocalDate
  )
}
