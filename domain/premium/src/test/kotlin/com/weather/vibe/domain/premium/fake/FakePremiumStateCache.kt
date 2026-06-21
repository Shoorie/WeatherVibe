package com.weather.vibe.domain.premium.fake

import com.weather.vibe.domain.premium.cache.PremiumStateCache
import com.weather.vibe.domain.premium.model.PremiumState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

internal class FakePremiumStateCache(
  initial: PremiumState = PremiumState.NONE
) : PremiumStateCache {

  private val state = MutableStateFlow(initial)

  var readError: Throwable? = null

  val current: PremiumState
    get() = state.value

  override fun observe(): Flow<PremiumState> = flow {
    readError?.let { throw it }
    emitAll(state)
  }

  override suspend fun update(change: (PremiumState) -> PremiumState) {
    state.update(change)
  }
}
