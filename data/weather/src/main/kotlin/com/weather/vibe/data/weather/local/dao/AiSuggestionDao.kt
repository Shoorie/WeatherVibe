package com.weather.vibe.data.weather.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.weather.local.entity.AiSuggestionEntity

@Dao
interface AiSuggestionDao {

  @Query("SELECT * FROM ai_suggestion WHERE weatherKeyHash = :keyHash AND tone = :tone LIMIT 1")
  suspend fun get(keyHash: String, tone: String): AiSuggestionEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(entity: AiSuggestionEntity)
}
