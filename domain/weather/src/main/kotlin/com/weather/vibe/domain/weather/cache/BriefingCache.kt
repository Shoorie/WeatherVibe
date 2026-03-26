package com.weather.vibe.domain.weather.cache

import java.time.LocalDate

interface BriefingCache {
  suspend fun get(cityName: String, date: LocalDate): String?
  suspend fun save(cityName: String, date: LocalDate, text: String)
}
