package com.weather.vibe.domain.profile.usecase

import com.weather.vibe.domain.profile.cache.ProfileCache
import org.koin.core.annotation.Factory

@Factory
class SaveUsername internal constructor(
  private val cache: ProfileCache
) {

  suspend operator fun invoke(username: String) {
    cache.saveUsername(username = username)
  }
}
