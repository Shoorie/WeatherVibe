package com.weather.vibe.data.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.profile.persistence.ProfileCacheData
import com.weather.vibe.data.profile.persistence.ProfileDataStorePrefs
import com.weather.vibe.data.profile.persistence.ProfileQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.profile")
class DataProfileModule {

  @Single
  @ProfileQualifier
  fun provideProfileDataStore(context: Context): DataStore<ProfileCacheData> =
    ProfileDataStorePrefs().get(context)
}
