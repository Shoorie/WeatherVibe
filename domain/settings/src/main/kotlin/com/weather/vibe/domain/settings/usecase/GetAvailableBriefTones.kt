package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import org.koin.core.annotation.Factory

@Factory
class GetAvailableBriefTones {

  operator fun invoke(): List<BriefTone> =
    listOf(
      WITTY_AND_FRIENDLY,
      FORMAL,
      HUMOROUS
    )
}
