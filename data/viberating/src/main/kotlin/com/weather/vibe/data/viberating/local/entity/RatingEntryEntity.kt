package com.weather.vibe.data.viberating.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
  tableName = "rating_entries",
  indices = [Index(value = ["date"])]
)
internal data class RatingEntryEntity(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Long = 0,

  @ColumnInfo(name = "date")
  val date: LocalDate,

  @ColumnInfo(name = "rating")
  val rating: Int,

  @Embedded(prefix = "w_")
  val weather: WeatherSnapshotEmbedded,

  @ColumnInfo(name = "created_at_epoch_ms")
  val createdAtEpochMs: Long,

  @ColumnInfo(name = "note")
  val note: String?
)
