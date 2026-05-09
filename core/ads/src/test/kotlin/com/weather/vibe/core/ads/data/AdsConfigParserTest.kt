package com.weather.vibe.core.ads.data

import com.weather.vibe.core.ads.fixture.AdsConfigFixtures.DISABLED
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class AdsConfigParserTest {

  private val parser = AdsConfigParser()

  @Test
  fun `given valid json with placement, when parsed, then global enabled is preserved`() {
    val result = parser.parse(VALID_JSON_WITH_HOME_PLACEMENT)

    expectThat(result.globalEnabled).isEqualTo(true)
  }

  @Test
  fun `given valid json with placement, when parsed, then placement key is registered`() {
    val result = parser.parse(VALID_JSON_WITH_HOME_PLACEMENT)

    expectThat(result.placements).containsKey(HOME_BOTTOM_KEY)
  }

  @Test
  fun `given valid json with placement, when parsed, then placement enabled is preserved`() {
    val result = parser.parse(VALID_JSON_WITH_HOME_PLACEMENT)

    expectThat(result.placements[HOME_BOTTOM_KEY]?.enabled).isEqualTo(true)
  }

  @Test
  fun `given malformed json, when parsed, then return disabled config`() {
    val result = parser.parse(MALFORMED_JSON)

    expectThat(result).isEqualTo(DISABLED)
  }

  @Test
  fun `given empty json object, when parsed, then global is disabled`() {
    val result = parser.parse(EMPTY_JSON_OBJECT)

    expectThat(result.globalEnabled).isEqualTo(false)
  }

  @Test
  fun `given empty json object, when parsed, then placements are empty`() {
    val result = parser.parse(EMPTY_JSON_OBJECT)

    expectThat(result.placements).isEmpty()
  }

  @Test
  fun `given json with unknown keys, when parsed, then known fields are read`() {
    val result = parser.parse(JSON_WITH_FUTURE_FIELD)

    expectThat(result.globalEnabled).isEqualTo(true)
  }

  private companion object {

    const val HOME_BOTTOM_KEY = "home_bottom"
    const val MALFORMED_JSON = "not json at all"
    const val EMPTY_JSON_OBJECT = "{}"

    val VALID_JSON_WITH_HOME_PLACEMENT = """
      {
        "globalEnabled": true,
        "placements": {
          "$HOME_BOTTOM_KEY": { "enabled": true }
        }
      }
    """.trimIndent()

    val JSON_WITH_FUTURE_FIELD = """
      {
        "globalEnabled": true,
        "futureField": 42,
        "placements": {}
      }
    """.trimIndent()
  }
}
