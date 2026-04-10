package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

class GetAvailableBriefTonesTest {

  private val getAvailableBriefTones = GetAvailableBriefTones()

  @Test
  fun `when available tones requested, then returned in presentation order`() {

    val result = getAvailableBriefTones()

    expectThat(result).containsExactly(WITTY_AND_FRIENDLY, FORMAL, HUMOROUS)
  }
}
