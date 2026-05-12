package com.weather.vibe.data.ads.config.parser

import com.weather.vibe.data.ads.fixture.AdsConfigJsonFixtures.EMPTY_OBJECT
import com.weather.vibe.data.ads.fixture.AdsConfigJsonFixtures.MALFORMED
import com.weather.vibe.data.ads.fixture.AdsConfigJsonFixtures.WITH_FUTURE_FIELD
import com.weather.vibe.data.ads.fixture.AdsConfigJsonFixtures.WITH_HOME_PLACEMENT
import com.weather.vibe.domain.ads.config.AdsConfig
import com.weather.vibe.domain.ads.placement.AdPlacement.HomeBottom
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class AdsConfigParserTest {

  private val parser = AdsConfigParser()

  @Test
  fun `given valid json with placement, then global enabled is preserved`() {

    val result = parser.parse(WITH_HOME_PLACEMENT)

    expectThat(result.globalEnabled).isEqualTo(true)
  }

  @Test
  fun `given valid json with placement, then placement key is registered`() {

    val result = parser.parse(WITH_HOME_PLACEMENT)

    expectThat(result.placements).containsKey(HomeBottom.key)
  }

  @Test
  fun `given valid json with placement, then placement enabled is preserved`() {

    val result = parser.parse(WITH_HOME_PLACEMENT)

    expectThat(result.placements[HomeBottom.key]?.enabled).isEqualTo(true)
  }

  @Test
  fun `given malformed json, then return disabled config`() {

    val result = parser.parse(MALFORMED)

    expectThat(result).isEqualTo(AdsConfig.Disabled)
  }

  @Test
  fun `given empty json object, then global is disabled`() {

    val result = parser.parse(EMPTY_OBJECT)

    expectThat(result.globalEnabled).isEqualTo(false)
  }

  @Test
  fun `given empty json object, then placements are empty`() {

    val result = parser.parse(EMPTY_OBJECT)

    expectThat(result.placements).isEmpty()
  }

  @Test
  fun `given json with unknown keys, then known fields are read`() {

    val result = parser.parse(WITH_FUTURE_FIELD)

    expectThat(result.globalEnabled).isEqualTo(true)
  }
}
