package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class IsWelcomeOnboardingSeen internal constructor(
  private val observeUserSettings: ObserveUserSettings
) {

  operator fun invoke(): Flow<Boolean> =
    observeUserSettings().map { result ->
      result.getOrNull()
        ?.let(UserSettings::welcomeOnboardingSeen)
        ?: NOT_SEEN_BY_DEFAULT
    }

  private companion object {
    const val NOT_SEEN_BY_DEFAULT = false
  }
}
