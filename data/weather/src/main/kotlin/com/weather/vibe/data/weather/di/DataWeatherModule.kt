package com.weather.vibe.data.weather.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.weather.local.WeatherDatabase
import com.weather.vibe.data.weather.local.dao.AiSuggestionDao
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.weather")
class DataWeatherModule {

  @Single
  fun provideAiSuggestionDao(database: WeatherDatabase): AiSuggestionDao =
    database.aiSuggestionDao()

  @Single
  fun provideWeatherCacheDao(database: WeatherDatabase): WeatherCacheDao =
    database.weatherCacheDao()

  @Single
  fun provideWeatherDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
      context = context,
      klass = WeatherDatabase::class.java,
      name = DATABASE_NAME
    ).fallbackToDestructiveMigration(false)
      .build()

  private companion object {
    const val DATABASE_NAME = "weather.db"
  }
}
