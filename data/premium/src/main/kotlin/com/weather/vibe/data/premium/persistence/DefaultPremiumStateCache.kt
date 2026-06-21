package com.weather.vibe.data.premium.persistence

import androidx.datastore.core.DataStore
import com.weather.vibe.data.premium.persistence.mapper.PremiumStateCacheMapper
import com.weather.vibe.domain.premium.cache.PremiumStateCache
import com.weather.vibe.domain.premium.model.PremiumState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [PremiumStateCache::class])
internal class DefaultPremiumStateCache(
  @param:PremiumStateQualifier
  private val dataStore: DataStore<PremiumStateCacheData>,
  private val mapper: PremiumStateCacheMapper
) : PremiumStateCache {

  override fun observe(): Flow<PremiumState> =
    dataStore.data.map(mapper::toDomain)

  override suspend fun update(change: (PremiumState) -> PremiumState) {
    dataStore.updateData { previous ->
      val updated = change(mapper.toDomain(previous))
      mapper.toCache(previous = previous, state = updated)
    }
  }
}
