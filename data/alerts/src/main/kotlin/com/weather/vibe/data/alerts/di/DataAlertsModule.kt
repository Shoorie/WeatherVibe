package com.weather.vibe.data.alerts.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.alerts.persistence.AlertDedupeData
import com.weather.vibe.data.alerts.persistence.AlertDedupeDataStorePrefs
import com.weather.vibe.data.alerts.persistence.AlertDedupeQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.alerts")
class DataAlertsModule {

  @Single
  @AlertDedupeQualifier
  fun provideAlertDedupeDataStore(context: Context): DataStore<AlertDedupeData> =
    AlertDedupeDataStorePrefs().get(context)
}
