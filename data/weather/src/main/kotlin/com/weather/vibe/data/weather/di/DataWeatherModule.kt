package com.weather.vibe.data.weather.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.weather.local.WeatherDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.weather")
class DataWeatherModule {

  @Single
  fun provideWeatherDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db").build()

  @Single
  fun provideWeatherCacheDao(database: WeatherDatabase): WeatherCacheDao =
    database.weatherCacheDao()
}
