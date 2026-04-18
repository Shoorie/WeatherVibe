package com.weather.vibe.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.dao.WeatherSuggestionDao
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
import com.weather.vibe.data.weather.local.entity.WeatherSuggestionEntity

@Database(
  entities = [
    WeatherCacheEntity::class,
    WeatherSuggestionEntity::class
  ],
  version = 3,
  exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {
  abstract fun weatherCacheDao(): WeatherCacheDao
  abstract fun weatherSuggestionDao(): WeatherSuggestionDao
}
