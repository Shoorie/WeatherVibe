package com.weather.vibe.data.ads.fixture

internal object AdsConfigJsonFixtures {

  const val MALFORMED = "not json at all"
  const val EMPTY_OBJECT = "{}"

  val WITH_HOME_PLACEMENT = """
    {
      "globalEnabled": true,
      "placements": {
        "home_bottom": { "enabled": true }
      }
    }
  """.trimIndent()

  val WITH_FUTURE_FIELD = """
    {
      "globalEnabled": true,
      "futureField": 42,
      "placements": {}
    }
  """.trimIndent()
}
