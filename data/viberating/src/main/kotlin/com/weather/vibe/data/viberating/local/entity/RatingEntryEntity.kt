package com.weather.vibe.data.viberating.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "rating_entries")
internal data class RatingEntryEntity(

  @PrimaryKey
  @ColumnInfo(name = "date")
  val date: LocalDate,

  @ColumnInfo(name = "rating")
  val rating: Int,

  @ColumnInfo(name = "note")
  val note: String,

  @Embedded(prefix = "w_")
  val weather: WeatherSnapshotEmbedded,

  @ColumnInfo(name = "created_at_epoch_ms")
  val createdAtEpochMs: Long
)
