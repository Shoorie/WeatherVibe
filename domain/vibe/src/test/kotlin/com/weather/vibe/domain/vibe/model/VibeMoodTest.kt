package com.weather.vibe.domain.vibe.model

import com.weather.vibe.domain.vibe.model.VibeMood.DREARY
import com.weather.vibe.domain.vibe.model.VibeMood.OKAY
import com.weather.vibe.domain.vibe.model.VibeMood.PLEASANT
import com.weather.vibe.domain.vibe.model.VibeMood.RADIANT
import com.weather.vibe.domain.vibe.model.VibeMood.ROUGH
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class VibeMoodTest {

  @Test
  fun `when score reaches eighty five, then mood is radiant`() {

    expectThat(VibeMood.from(score = 85)).isEqualTo(RADIANT)
  }

  @Test
  fun `when score between sixty five and eighty four, then mood is pleasant`() {

    expectThat(VibeMood.from(score = 70)).isEqualTo(PLEASANT)
  }

  @Test
  fun `when score between forty five and sixty four, then mood is okay`() {

    expectThat(VibeMood.from(score = 50)).isEqualTo(OKAY)
  }

  @Test
  fun `when score between twenty five and forty four, then mood is dreary`() {

    expectThat(VibeMood.from(score = 30)).isEqualTo(DREARY)
  }

  @Test
  fun `when score below twenty five, then mood is rough`() {

    expectThat(VibeMood.from(score = 10)).isEqualTo(ROUGH)
  }
}
