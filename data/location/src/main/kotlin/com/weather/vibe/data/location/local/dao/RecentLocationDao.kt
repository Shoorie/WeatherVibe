package com.weather.vibe.data.location.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.location.local.entity.RecentLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentLocationDao {

  @Query("SELECT * FROM recent_locations WHERE id = :id")
  suspend fun findById(id: Long): RecentLocationEntity?

  @Query("SELECT * FROM recent_locations ORDER BY timestamp DESC LIMIT :limit")
  suspend fun getRecent(limit: Int): List<RecentLocationEntity>

  @Query("SELECT * FROM recent_locations ORDER BY timestamp DESC LIMIT :limit")
  fun observeRecent(limit: Int): Flow<List<RecentLocationEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: RecentLocationEntity)
}
