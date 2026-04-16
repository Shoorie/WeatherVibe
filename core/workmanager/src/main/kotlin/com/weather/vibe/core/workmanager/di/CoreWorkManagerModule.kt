package com.weather.vibe.core.workmanager.di

import android.content.Context
import androidx.work.WorkManager
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class CoreWorkManagerModule {

  @Single
  fun workManager(context: Context): WorkManager =
    WorkManager.getInstance(context)
}
