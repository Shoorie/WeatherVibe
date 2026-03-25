package com.weather.vibe.data.settings.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.settings.local.SettingsDatabase
import com.weather.vibe.data.settings.local.dao.SettingsDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.settings")
class DataSettingsModule {

  @Single
  fun provideSettingsDatabase(context: Context): SettingsDatabase =
    Room.databaseBuilder(
      context = context,
      klass = SettingsDatabase::class.java,
      name = "settings.db"
    ).build()

  @Single
  fun provideSettingsDao(database: SettingsDatabase): SettingsDao =
    database.settingsDao()
}

