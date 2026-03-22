package com.weather.vibe.data.location.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.location.local.LocationDatabase
import com.weather.vibe.data.location.local.dao.RecentLocationDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.location")
class DataLocationModule {

  @Single
  fun provideLocationDatabase(context: Context): LocationDatabase =
    Room.databaseBuilder(
      context = context,
      klass = LocationDatabase::class.java,
      name = "location.db"
    ).build()

  @Single
  fun provideRecentLocationDao(
    database: LocationDatabase
  ): RecentLocationDao =
    database.recentLocationDao()
}
