package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.BriefTone
import org.koin.core.annotation.Factory

@Factory
class SelectBriefTone internal constructor(
  private val cache: SettingsCache
) {

  suspend operator fun invoke(tone: BriefTone) {
    cache.update { it.withBriefTone(tone) }
  }
}
