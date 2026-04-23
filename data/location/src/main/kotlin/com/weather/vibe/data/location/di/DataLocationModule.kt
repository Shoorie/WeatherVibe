package com.weather.vibe.data.location.di

import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.vibe.data.location.local.LocationDatabase
import com.weather.vibe.data.location.local.dao.LocationFavoriteDao
import com.weather.vibe.data.location.local.dao.LocationWeatherSnapshotDao
import com.weather.vibe.data.location.local.dao.RecentLocationDao
import com.weather.vibe.data.location.local.migration.Migration1To2
import com.weather.vibe.data.location.local.migration.Migration2To3
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.location")
class DataLocationModule {

  @Single
  fun provideLocationDatabase(context: Context): LocationDatabase =
    Room.databaseBuilder(
      context = context,
      klass = LocationDatabase::class.java,
      name = "location.db"
    )
      .addMigrations(Migration1To2, Migration2To3)
      .build()

  @Single
  fun provideRecentLocationDao(database: LocationDatabase): RecentLocationDao =
    database.recentLocationDao()

  @Single
  fun provideFavoriteDao(database: LocationDatabase): LocationFavoriteDao =
    database.favoriteDao()

  @Single
  fun provideLocationWeatherSnapshotDao(
    database: LocationDatabase
  ): LocationWeatherSnapshotDao =
    database.locationWeatherSnapshotDao()

  @Single
  fun provideGeocoder(context: Context): Geocoder =
    Geocoder(context)

  @Single
  fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(context)
}
