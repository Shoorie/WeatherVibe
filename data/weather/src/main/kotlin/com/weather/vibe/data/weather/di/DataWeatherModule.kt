package com.weather.vibe.data.weather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.room.Room
import com.weather.vibe.data.weather.local.WeatherDatabase
import com.weather.vibe.data.weather.local.dao.WeatherCacheDao
import com.weather.vibe.data.weather.persistence.BriefingCacheData
import com.weather.vibe.data.weather.persistence.BriefingDataStorePrefs
import com.weather.vibe.data.weather.persistence.BriefingDataStoreQualifier
import com.weather.vibe.data.weather.persistence.MoodPlaylistCacheData
import com.weather.vibe.data.weather.persistence.MoodPlaylistDataStorePrefs
import com.weather.vibe.data.weather.persistence.MoodPlaylistDataStoreQualifier
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
  @BriefingDataStoreQualifier
  fun provideBriefingDataStore(context: Context): DataStore<BriefingCacheData> =
    BriefingDataStorePrefs().get(context)

  @Single
  @MoodPlaylistDataStoreQualifier
  fun provideMoodPlaylistDataStore(context: Context): DataStore<MoodPlaylistCacheData> =
    MoodPlaylistDataStorePrefs().get(context)
}
