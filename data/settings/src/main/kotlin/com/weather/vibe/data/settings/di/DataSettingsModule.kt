package com.weather.vibe.data.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.settings.persistence.UserSettingsCacheData
import com.weather.vibe.data.settings.persistence.UserSettingsDataStorePrefs
import com.weather.vibe.data.settings.persistence.UserSettingsQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weather.vibe.data.settings")
class DataSettingsModule {

  @Single
  @UserSettingsQualifier
  fun provideUserSettings(context: Context): DataStore<UserSettingsCacheData> =
    UserSettingsDataStorePrefs().get(context)
}
