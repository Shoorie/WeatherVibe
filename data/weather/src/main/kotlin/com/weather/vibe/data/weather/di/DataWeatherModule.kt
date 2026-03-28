package com.weather.vibe.data.weather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.room.Room
import com.weather.vibe.data.weather.local.WeatherDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.persistence.WeatherAiCacheData
import com.weather.vibe.data.weather.persistence.WeatherAiDataStorePrefs
import com.weather.vibe.data.weather.persistence.WeatherAiDataStoreQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.weather")
class DataWeatherModule {

  @Single
  fun provideWeatherDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
      context = context,
      klass = WeatherDatabase::class.java,
      name = "weather.db"
    ).build()

  @Single
  fun provideWeatherCacheDao(database: WeatherDatabase): WeatherCacheDao =
    database.weatherCacheDao()

  @Single
  @WeatherAiDataStoreQualifier
  fun provideWeatherAiDataStore(context: Context): DataStore<WeatherAiCacheData> =
    WeatherAiDataStorePrefs().get(context)
}
