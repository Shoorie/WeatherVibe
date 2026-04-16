package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.UserSettings
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class IsMorningBriefEnabled internal constructor(
  private val observeUserSettings: ObserveUserSettings
) {

  suspend operator fun invoke(): Boolean =
    observeUserSettings().first()
      .getOrNull()
      ?.let(UserSettings::morningBriefEnabled)
      ?: DISABLED_BY_DEFAULT

  private companion object {
    const val DISABLED_BY_DEFAULT = false
  }
}
