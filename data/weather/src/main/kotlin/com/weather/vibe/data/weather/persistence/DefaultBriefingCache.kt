package com.weather.vibe.data.weather.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.domain.weather.cache.BriefingCache
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single(binds = [BriefingCache::class])
internal class DefaultBriefingCache(
  @param:BriefingDataStoreQualifier
  private val dataStore: DataStore<BriefingCacheData>
) : BriefingCache {

  override suspend fun get(cityName: String, date: LocalDate): String? {
    val data = dataStore.data.first()
    return data.briefingText.takeIf {
      data.cityName == cityName &&
        data.date == date.toString() &&
        it.isNotBlank()
    }
  }

  override suspend fun save(cityName: String, date: LocalDate, text: String) {
    dataStore.updateData {
      it.toBuilder()
        .setCityName(cityName)
        .setDate(date.toString())
        .setBriefingText(text)
        .build()
    }
  }
}
