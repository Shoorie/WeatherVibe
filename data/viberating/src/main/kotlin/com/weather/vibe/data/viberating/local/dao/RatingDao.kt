package com.weather.vibe.data.viberating.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.weather.vibe.data.viberating.local.entity.RatingEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
internal interface RatingDao {

  @Query("SELECT * FROM rating_entries ORDER BY date DESC")
  fun observeAll(): Flow<List<RatingEntryEntity>>

  @Query("SELECT * FROM rating_entries WHERE date = :date LIMIT 1")
  fun observeForDate(date: LocalDate): Flow<RatingEntryEntity?>

  @Upsert
  suspend fun upsert(entry: RatingEntryEntity)
}
