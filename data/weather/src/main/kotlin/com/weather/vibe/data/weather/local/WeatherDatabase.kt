package com.weather.vibe.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.weather.local.dao.AiSuggestionDao
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.entity.AiSuggestionEntity
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity

@Database(
  entities = [
    AiSuggestionEntity::class,
    WeatherCacheEntity::class
  ],
  version = 2,
  exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {
  abstract fun aiSuggestionDao(): AiSuggestionDao
  abstract fun weatherCacheDao(): WeatherCacheDao
}
