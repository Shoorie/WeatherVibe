package com.weather.vibe.core.ads.domain.config

import com.weather.vibe.core.remoteconfig.domain.flag.StringFeatureFlag

internal object AdsConfigKeys {

  val AdsConfigFlag = StringFeatureFlag(
    default = """{"globalEnabled":false,"placements":{}}""",
    key = "ads_config"
  )
}
