package com.weather.vibe.data.location.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.location.local.dao.LocationFavoriteDao
import com.weather.vibe.data.location.local.dao.LocationWeatherSnapshotDao
import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.entity.LocationFavoriteEntity
import com.weather.vibe.data.location.local.entity.LocationWeatherSnapshotEntity
import com.weather.vibe.data.location.local.entity.RecentLocationEntity

@Database(
  entities = [
    RecentLocationEntity::class,
    LocationFavoriteEntity::class,
    LocationWeatherSnapshotEntity::class
  ],
  version = 3
)
abstract class LocationDatabase : RoomDatabase() {
  abstract fun favoriteDao(): LocationFavoriteDao
  abstract fun locationWeatherSnapshotDao(): LocationWeatherSnapshotDao
  abstract fun recentLocationDao(): RecentLocationDao
}
