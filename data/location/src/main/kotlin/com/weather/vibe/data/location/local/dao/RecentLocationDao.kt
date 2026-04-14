package com.weather.vibe.data.location.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.location.local.entity.RecentLocationEntity

@Dao
interface RecentLocationDao {

  @Query("SELECT * FROM recent_locations WHERE id = :id")
  suspend fun findById(id: Long): RecentLocationEntity?

  @Query("SELECT * FROM recent_locations ORDER BY timestamp DESC LIMIT :limit")
  suspend fun getRecent(limit: Int): List<RecentLocationEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: RecentLocationEntity)
}
