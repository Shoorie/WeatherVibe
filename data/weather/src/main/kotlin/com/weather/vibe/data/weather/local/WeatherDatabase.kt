package com.weather.vibe.data.weather.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity

@Database(
  entities = [WeatherCacheEntity::class],
  version = 1,
  exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {
  abstract fun weatherCacheDao(): WeatherCacheDao
}
