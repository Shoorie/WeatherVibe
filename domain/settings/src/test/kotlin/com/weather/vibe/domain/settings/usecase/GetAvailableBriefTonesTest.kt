package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.BriefTone.CINEMATIC
import com.weather.vibe.domain.settings.model.BriefTone.COACH
import com.weather.vibe.domain.settings.model.BriefTone.CYNIC
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.settings.model.BriefTone.HUMOROUS
import com.weather.vibe.domain.settings.model.BriefTone.RPG
import com.weather.vibe.domain.settings.model.BriefTone.SCI_FI
import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

class GetAvailableBriefTonesTest {

  private val getAvailableBriefTones = GetAvailableBriefTones()

  @Test
  fun `when available tones requested, then returned with free tones first`() {

    val result = getAvailableBriefTones()

    expectThat(result).containsExactly(
      WITTY_AND_FRIENDLY,
      FORMAL,
      HUMOROUS,
      COACH,
      SCI_FI,
      RPG,
      CINEMATIC,
      CYNIC
    )
  }
}
