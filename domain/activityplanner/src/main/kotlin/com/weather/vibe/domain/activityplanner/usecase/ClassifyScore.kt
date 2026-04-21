package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ScoreTier
import org.koin.core.annotation.Factory

@Factory
class ClassifyScore {

  operator fun invoke(score: Int): ScoreTier =
    ScoreTier.entries.first { score >= it.minScore }
}
