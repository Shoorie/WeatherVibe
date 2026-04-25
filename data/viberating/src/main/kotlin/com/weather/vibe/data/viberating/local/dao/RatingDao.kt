package com.weather.vibe.data.viberating.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.weather.vibe.data.viberating.local.entity.RatingEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
internal interface RatingDao {

  @Query("SELECT * FROM rating_entries ORDER BY date DESC, created_at_epoch_ms DESC")
  fun observeAll(): Flow<List<RatingEntryEntity>>

  @Query("SELECT * FROM rating_entries WHERE date = :date ORDER BY created_at_epoch_ms DESC")
  fun observeForDate(date: LocalDate): Flow<List<RatingEntryEntity>>

  @Insert
  suspend fun insert(entry: RatingEntryEntity)
}
