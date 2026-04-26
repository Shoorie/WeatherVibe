package com.weather.vibe.data.appearance.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.appearance.persistence.AppearanceCacheData
import com.weather.vibe.data.appearance.persistence.AppearanceDataStorePrefs
import com.weather.vibe.data.appearance.persistence.AppearanceQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.appearance")
class DataAppearanceModule {

  @Single
  @AppearanceQualifier
  fun provideAppearanceDataStore(context: Context): DataStore<AppearanceCacheData> =
    AppearanceDataStorePrefs().get(context)
}
