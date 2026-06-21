package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.BriefTone
import org.koin.core.annotation.Factory

@Factory
class GetAvailableBriefTones {

  operator fun invoke(): List<BriefTone> =
    BriefTone.entries
}
