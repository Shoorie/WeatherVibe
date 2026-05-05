package com.weather.vibe.data.weather.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.weather.local.WeatherDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.local.dao.WeatherSuggestionDao
import com.weather.vibe.data.weather.local.migration.Migration2To3
import com.weather.vibe.data.weather.local.migration.Migration3To4
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.weather")
class DataWeatherModule {

  @Single
  fun provideWeatherCacheDao(database: WeatherDatabase): WeatherCacheDao =
    database.weatherCacheDao()

  @Single
  fun provideWeatherSuggestionDao(database: WeatherDatabase): WeatherSuggestionDao =
    database.weatherSuggestionDao()

  @Single
  fun provideWeatherDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
      context = context,
      klass = WeatherDatabase::class.java,
      name = DATABASE_NAME
    ).addMigrations(Migration2To3, Migration3To4)
      .fallbackToDestructiveMigration(false)
      .build()

  private companion object {
    const val DATABASE_NAME = "weather.db"
  }
}
