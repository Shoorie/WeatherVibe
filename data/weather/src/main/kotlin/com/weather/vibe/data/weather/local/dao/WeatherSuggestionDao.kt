package com.weather.vibe.data.weather.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.weather.local.entity.WeatherSuggestionEntity

@Dao
interface WeatherSuggestionDao {

  @Query("DELETE FROM ai_suggestion WHERE weatherKeyHash = :keyHash AND tone = :tone")
  suspend fun delete(keyHash: String, tone: String)

  @Query("SELECT * FROM ai_suggestion WHERE weatherKeyHash = :keyHash AND tone = :tone ORDER BY fetchedAt DESC LIMIT 1")
  suspend fun get(keyHash: String, tone: String): WeatherSuggestionEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(entity: WeatherSuggestionEntity)
}
