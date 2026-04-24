package com.weather.vibe.data.viberating.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weather.vibe.data.viberating.local.converter.RatingEntryConverters
import com.weather.vibe.data.viberating.local.dao.RatingDao
import com.weather.vibe.data.viberating.local.entity.RatingEntryEntity

@Database(
  entities = [RatingEntryEntity::class],
  version = 2,
  exportSchema = true
)
@TypeConverters(RatingEntryConverters::class)
internal abstract class VibeRatingDatabase : RoomDatabase() {
  abstract fun ratingDao(): RatingDao
}
