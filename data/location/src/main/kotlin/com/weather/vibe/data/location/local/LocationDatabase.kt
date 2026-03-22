package com.weather.vibe.data.location.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.entity.RecentLocationEntity

@Database(
  entities = [RecentLocationEntity::class],
  version = 1
)
abstract class LocationDatabase : RoomDatabase() {
  abstract fun recentLocationDao(): RecentLocationDao
}
