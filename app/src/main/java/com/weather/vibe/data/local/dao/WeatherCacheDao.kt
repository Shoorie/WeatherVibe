package com.weather.vibe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.local.entity.WeatherCacheEntity

@Dao
interface WeatherCacheDao {

    @Query("SELECT * FROM weather_cache WHERE locationId = :locationId")
    suspend fun getWeather(locationId: String): WeatherCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWeather(entity: WeatherCacheEntity)
}
