package com.weather.vibe.data.location.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.vibe.data.location.local.entity.LocationWeatherSnapshotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationWeatherSnapshotDao {

  @Query("SELECT * FROM location_weather_snapshot")
  fun observeAll(): Flow<List<LocationWeatherSnapshotEntity>>

  @Query("SELECT * FROM location_weather_snapshot WHERE locationId = :locationId")
  suspend fun findById(locationId: Long): LocationWeatherSnapshotEntity?

  @Query("DELETE FROM location_weather_snapshot WHERE locationId = :locationId")
  suspend fun deleteById(locationId: Long)

  @Query("SELECT EXISTS(SELECT 1 FROM favorite_locations WHERE locationId = :locationId)")
  suspend fun favoriteExists(locationId: Long): Boolean

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(entity: LocationWeatherSnapshotEntity)
}
