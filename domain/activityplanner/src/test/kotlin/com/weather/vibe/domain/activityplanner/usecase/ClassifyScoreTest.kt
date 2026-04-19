package com.weather.vibe.domain.activityplanner.usecase

import com.weather.vibe.domain.activityplanner.model.ScoreTier.EXCELLENT
import com.weather.vibe.domain.activityplanner.model.ScoreTier.FAIR
import com.weather.vibe.domain.activityplanner.model.ScoreTier.GOOD
import com.weather.vibe.domain.activityplanner.model.ScoreTier.POOR
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ClassifyScoreTest {

  private val classifyScore = ClassifyScore()

  @Test
  fun `given score above 85, then excellent returned`() {

    val tier = classifyScore(score = 90)

    expectThat(tier).isEqualTo(EXCELLENT)
  }

  @Test
  fun `given score between 70 and 84, then good returned`() {

    val tier = classifyScore(score = 75)

    expectThat(tier).isEqualTo(GOOD)
  }

  @Test
  fun `given score between 50 and 69, then fair returned`() {

    val tier = classifyScore(score = 55)

    expectThat(tier).isEqualTo(FAIR)
  }

  @Test
  fun `given score below 50, then poor returned`() {

    val tier = classifyScore(score = 30)

    expectThat(tier).isEqualTo(POOR)
  }

  @Test
  fun `given score exactly at excellent threshold, then excellent returned`() {

    val tier = classifyScore(score = 85)

    expectThat(tier).isEqualTo(EXCELLENT)
  }
}
