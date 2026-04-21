package com.weather.vibe.domain.profile.usecase

import com.weather.vibe.domain.profile.cache.ProfileCache
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveUsername internal constructor(
  private val cache: ProfileCache
) {

  operator fun invoke(): Flow<String> =
    cache.observeUsername()
}
