package com.weather.vibe.data.settings.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.vibe.data.settings.local.dao.SettingsDao
import com.weather.vibe.data.settings.local.entity.SettingsEntity

@Database(
  entities = [SettingsEntity::class],
  version = 1
)
abstract class SettingsDatabase : RoomDatabase() {
  abstract fun settingsDao(): SettingsDao
}

