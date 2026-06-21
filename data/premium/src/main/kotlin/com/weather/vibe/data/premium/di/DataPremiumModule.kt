package com.weather.vibe.data.premium.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.premium.persistence.PremiumStateCacheData
import com.weather.vibe.data.premium.persistence.PremiumStateDataStorePrefs
import com.weather.vibe.data.premium.persistence.PremiumStateQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.premium")
class DataPremiumModule {

  @Single
  @PremiumStateQualifier
  fun providePremiumState(context: Context): DataStore<PremiumStateCacheData> =
    PremiumStateDataStorePrefs().get(context)
}
