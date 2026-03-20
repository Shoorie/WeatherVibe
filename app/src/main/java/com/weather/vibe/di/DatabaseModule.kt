package com.weather.vibe.di

import androidx.room.Room
import com.weather.vibe.data.local.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }
    single { get<WeatherDatabase>().weatherCacheDao() }
}
