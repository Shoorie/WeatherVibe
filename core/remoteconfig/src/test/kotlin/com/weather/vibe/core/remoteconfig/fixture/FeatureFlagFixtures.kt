package com.weather.vibe.core.remoteconfig.fixture

import com.weather.vibe.core.remoteconfig.domain.flag.BooleanFeatureFlag
import com.weather.vibe.core.remoteconfig.domain.flag.StringFeatureFlag

internal object FeatureFlagFixtures {

  const val FLAG_KEY = "test_flag"
  const val BOOLEAN_DEFAULT = false
  const val STRING_DEFAULT = "default_value"

  val DEFAULT_BOOLEAN_FLAG = booleanFlag()
  val DEFAULT_STRING_FLAG = stringFlag()

  fun booleanFlag(
    default: Boolean = BOOLEAN_DEFAULT,
    key: String = FLAG_KEY
  ): BooleanFeatureFlag = BooleanFeatureFlag(default = default, key = key)

  fun stringFlag(
    default: String = STRING_DEFAULT,
    key: String = FLAG_KEY
  ): StringFeatureFlag = StringFeatureFlag(default = default, key = key)
}
