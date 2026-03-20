package com.weather.vibe

import android.app.Application
import com.weather.vibe.di.databaseModule
import com.weather.vibe.di.domainModule
import com.weather.vibe.di.networkModule
import com.weather.vibe.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherVibeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WeatherVibeApplication)
            modules(networkModule, databaseModule, domainModule, viewModelModule)
        }
    }
}
