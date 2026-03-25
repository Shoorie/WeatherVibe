package com.weather.vibe.data.settings.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.settings.local.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

  @Query("SELECT * FROM settingss")
  fun getAll(): Flow<List<SettingsEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(items: List<SettingsEntity>)
}

