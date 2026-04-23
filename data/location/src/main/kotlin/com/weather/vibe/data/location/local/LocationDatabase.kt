package com.weather.vibe.data.location.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.location.local.dao.FavoriteDao
import com.weather.vibe.data.location.local.dao.LocationWeatherSnapshotDao
import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.entity.FavoriteEntity
import com.weather.vibe.data.location.local.entity.LocationWeatherSnapshotEntity
import com.weather.vibe.data.location.local.entity.RecentLocationEntity

@Database(
  entities = [
    RecentLocationEntity::class,
    FavoriteEntity::class,
    LocationWeatherSnapshotEntity::class
  ],
  version = 2
)
abstract class LocationDatabase : RoomDatabase() {
  abstract fun favoriteDao(): FavoriteDao
  abstract fun locationWeatherSnapshotDao(): LocationWeatherSnapshotDao
  abstract fun recentLocationDao(): RecentLocationDao
}
