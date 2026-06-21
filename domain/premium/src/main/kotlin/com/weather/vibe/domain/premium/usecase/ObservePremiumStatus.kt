package com.weather.vibe.domain.premium.usecase

import com.weather.vibe.domain.premium.cache.PremiumStateCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class ObservePremiumStatus internal constructor(
  private val premiumStateCache: PremiumStateCache
) {

  operator fun invoke(): Flow<Result<Boolean>> =
    premiumStateCache.observe()
      .map { state -> success(state.isPremium) }
      .catch { emit(failure(it)) }
}
