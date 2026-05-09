package com.weather.vibe.core.ads.data

import com.weather.vibe.core.ads.domain.config.AdsConfig
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.isEqualTo

class AdsConfigParserTest {

  private val parser = AdsConfigParser()

  @Test
  fun `when valid json parsed, then return config with placement`() {
    val raw = """
      {
        "globalEnabled": true,
        "placements": {
          "home_bottom": { "enabled": true }
        }
      }
    """.trimIndent()

    val result = parser.parse(raw)

    expectThat(result.globalEnabled).isEqualTo(true)
    expectThat(result.placements).containsKey("home_bottom")
    expectThat(result.placements["home_bottom"]?.enabled).isEqualTo(true)
  }

  @Test
  fun `when malformed json parsed, then return disabled config`() {
    val raw = "not json at all"

    val result = parser.parse(raw)

    expectThat(result).isEqualTo(AdsConfig.Disabled)
  }

  @Test
  fun `when empty json parsed, then return defaults`() {
    val result = parser.parse("{}")

    expectThat(result.globalEnabled).isEqualTo(false)
    expectThat(result.placements).isEqualTo(emptyMap())
  }

  @Test
  fun `when unknown keys present, then ignore and parse known fields`() {
    val raw = """
      {
        "globalEnabled": true,
        "futureField": 42,
        "placements": {}
      }
    """.trimIndent()

    val result = parser.parse(raw)

    expectThat(result.globalEnabled).isEqualTo(true)
  }
}
