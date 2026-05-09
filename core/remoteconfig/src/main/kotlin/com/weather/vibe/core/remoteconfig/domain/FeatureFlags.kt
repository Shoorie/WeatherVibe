package com.weather.vibe.core.remoteconfig.domain

import com.weather.vibe.core.remoteconfig.domain.flag.BooleanFeatureFlag
import com.weather.vibe.core.remoteconfig.domain.flag.StringFeatureFlag
import kotlinx.coroutines.flow.Flow

interface FeatureFlags {

  val updates: Flow<Unit>

  fun bool(flag: BooleanFeatureFlag): Boolean

  fun string(flag: StringFeatureFlag): String
}
