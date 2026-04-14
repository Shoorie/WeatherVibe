package com.weather.vibe.data.widget.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.weather.vibe.data.widget.persistence.WidgetSnapshotCacheData
import com.weather.vibe.data.widget.persistence.WidgetSnapshotDataStorePrefs
import com.weather.vibe.data.widget.persistence.WidgetSnapshotQualifier
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.widget")
class DataWidgetModule {

  @Single
  @WidgetSnapshotQualifier
  fun provideWidgetSnapshot(context: Context): DataStore<WidgetSnapshotCacheData> =
    WidgetSnapshotDataStorePrefs().get(context)
}
