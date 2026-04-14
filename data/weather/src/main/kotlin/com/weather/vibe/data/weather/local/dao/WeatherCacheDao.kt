package com.weather.vibe.data.weather.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.weather.local.entity.WeatherCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherCacheDao {

  @Query("SELECT * FROM weather_cache WHERE locationId = :locationId")
  suspend fun getWeather(locationId: String): WeatherCacheEntity?

  @Query("SELECT * FROM weather_cache WHERE locationId = :locationId")
  fun observeWeather(locationId: String): Flow<WeatherCacheEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertWeather(entity: WeatherCacheEntity)
}
